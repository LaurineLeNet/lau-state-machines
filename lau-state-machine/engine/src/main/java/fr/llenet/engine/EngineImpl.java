package fr.llenet.engine;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.context.ProcessStatus;
import fr.llenet.engine.context.TransitionExecution;
import fr.llenet.engine.core.State;
import fr.llenet.engine.core.Transition;
import fr.llenet.engine.dao.EngineDao;
import fr.llenet.engine.dao.ProcessedObjectExecution;
import fr.llenet.engine.dao.ProcessedObjectTransition;
import fr.llenet.engine.definition.ProcessDefinition;
import fr.llenet.engine.definition.ProcessType;
import fr.llenet.engine.definition.ProcessedObject;
import fr.llenet.engine.helper.ProcessExecutionHelper;
import fr.llenet.engine.mapper.ParameterMapper;
import fr.llenet.engine.mapper.ProcessMapper;
import fr.llenet.engine.parameter.ParameterHelper;
import fr.llenet.engine.parameter.ParameterName;
import fr.llenet.engine.parameter.ParameterValue;
import fr.llenet.engine.service.TransitionExecutionService;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Log4j2
public class EngineImpl implements Engine {
    private final ProcessExecutionHelper processExecutionHelper;
    private final ParameterHelper parameterHelper;
    private final TransitionExecutionService transitionExecutionService;
    private final EngineDao engineDao;

    public EngineImpl(ProcessExecutionHelper processExecutionHelper, ParameterHelper parameterHelper, TransitionExecutionService transitionExecutionService, EngineDao dao) {
        this.processExecutionHelper = processExecutionHelper;
        this.parameterHelper = parameterHelper;
        this.transitionExecutionService = transitionExecutionService;
        this.engineDao = dao;
    }

    @Override
    public ProcessExecutionContext launch(ProcessType processType, Map<String, ParameterValue<?>> parameters) {
        ProcessDefinition definition = processExecutionHelper.definitionFromType(processType);

        ProcessedObject processedObject = definition.initProcessedObject();
        processedObject = engineDao.save(processedObject);

        log.info("[Processed Object {}", processedObject);

        parameters.put(ParameterName.PROCESSED_OBJECT.name(), ParameterValue.of(processedObject));
        parameters.put(ParameterName.PROCESS_TYPE.name(), ParameterValue.of(processType));

        String processExecutionId = processedObject.getId();

        ProcessExecutionContext processExecutionContext = processExecutionContextBuilder(processExecutionId, parameters);
        executeTransitions(processExecutionContext, definition.startState());
        finishAndSaveProcess(processExecutionContext, new ArrayList<>());
        return processExecutionContext;
    }

    @Override
    public ProcessExecutionContext relaunch(ProcessType processType, Map<String, ParameterValue<?>> parameters, String id) {

        ProcessDefinition definition = processExecutionHelper.definitionFromType(processType);
        ProcessedObjectExecution processedObjectExecution = engineDao.getById(id).orElseThrow(() -> new NoSuchElementException("No order matching id"));

        if (processedObjectExecution.getStatus() != ProcessStatus.PAUSED && processedObjectExecution.getStatus() != ProcessStatus.FAILED) {
            throw new IllegalArgumentException("Can't be relaunch");
        }

        ProcessExecutionContext processExecutionContext = processExecutionContextFromProcessObjectExecution(processedObjectExecution);
        processExecutionContext.addParameters(parameters);
        State firstState = definition.states().stream()
                .filter((state) -> state.getName().equals(processedObjectExecution.getLatestStateName()))
                .findFirst().orElseThrow(() -> new RuntimeException("No latest state match"));

        executeTransitions(processExecutionContext, firstState);
        finishAndSaveProcess(processExecutionContext, processedObjectExecution.getTransitions());

        return processExecutionContext;
    }

    private void finishAndSaveProcess(ProcessExecutionContext processExecutionContext, List<ProcessedObjectTransition> transitions) {

        if (processExecutionContext.getStatus() == ProcessStatus.RUNNING) {
            processExecutionContext.setStatus(ProcessStatus.FINISHED);
        }

        String latestStateName = "endState";
        if (processExecutionContext.getCurrentTransition() != null) {
            latestStateName = processExecutionContext.getCurrentTransition().getToState().getName();
        }
        ProcessedObjectExecution processedObjectExecution = ProcessMapper.toDatabase(processExecutionContext, latestStateName, transitions);

        Optional.ofNullable(processExecutionContext.getParameterValue(ParameterName.ERROR_MESSAGE.name(), String.class, false)).ifPresent(
                processedObjectExecution::setErrorMessage
        );
        engineDao.save(processedObjectExecution);
    }

    private ProcessExecutionContext processExecutionContextBuilder(String processExecutionId, Map<String, ParameterValue<?>> parameters) {
        return ProcessExecutionContext.builder()
                .executionId(processExecutionId)
                .parameters(parameters)
                .status(ProcessStatus.RUNNING)
                .build();
    }

    private ProcessExecutionContext processExecutionContextFromProcessObjectExecution(ProcessedObjectExecution processedObjectExecution) {
        return ProcessExecutionContext.builder()
                .executionId(processedObjectExecution.getId())
                .parameters(ParameterMapper.fromDbParameters(processedObjectExecution.getParameters()))
                .status(ProcessStatus.RUNNING)
                .creationDate(processedObjectExecution.getCreationDate())
                .build();
    }

    private void executeTransitions(ProcessExecutionContext processExecutionContext, State currentState) {
        Optional<Transition> nextTransition = findNextTransition(processExecutionContext, currentState);

        while (processExecutionContext.getStatus() == ProcessStatus.RUNNING && nextTransition.isPresent()) {
            try {
                processExecutionContext.setCurrentTransition(nextTransition.get());
                transitionExecutionService.execute(processExecutionContext, nextTransition.get());
                processExecutionContext.getTransitionExecutions().add(new TransitionExecution(nextTransition.get(), Instant.now().truncatedTo(ChronoUnit.MILLIS)));

                if (nextTransition.get().isProcessPaused()) {
                    processExecutionContext.setStatus(ProcessStatus.PAUSED);
                }
                nextTransition = findNextTransition(processExecutionContext, nextTransition.get().getToState());
            } catch (Exception e) {
                log.error("Error while executing : {}", processExecutionContext, e);
                processExecutionContext.setStatus(ProcessStatus.FAILED);
            }

        }
    }

    private Optional<Transition> findNextTransition(ProcessExecutionContext processExecutionContext, State currentState) {
        if (processExecutionContext.getStatus() != ProcessStatus.RUNNING) {
            return Optional.empty();
        }
        return currentState.getNextTransitions().stream()
                .filter(transition -> isEligibleTransition(processExecutionContext, transition))
                .findFirst();
    }

    private Boolean isEligibleTransition(ProcessExecutionContext processExecutionContext, Transition transition) {
        return processExecutionHelper.conditionsFromTransition(transition)
                .stream()
                .allMatch(condition -> condition.isValid(processExecutionContext));
    }
}
