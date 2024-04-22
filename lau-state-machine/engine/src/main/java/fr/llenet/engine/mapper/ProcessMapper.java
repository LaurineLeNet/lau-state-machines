package fr.llenet.engine.mapper;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.context.TransitionExecution;
import fr.llenet.engine.core.Condition;
import fr.llenet.engine.dao.ProcessedObjectExecution;
import fr.llenet.engine.dao.ProcessedObjectTransition;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Collections;
import java.util.List;

public class ProcessMapper {
    public static ProcessedObjectExecution toDatabase(ProcessExecutionContext processExecutionContext, String latestStateName, List<ProcessedObjectTransition> transitions) {
        if(ObjectUtils.isNotEmpty(processExecutionContext.getTransitionExecutions())) {
            transitions.addAll(fromTransitionContext(processExecutionContext.getTransitionExecutions()));
        }
        return ProcessedObjectExecution.builder()
                .id(processExecutionContext.getExecutionId())
                .latestStateName(latestStateName)
                .parameters(ParameterMapper.toDatabase(processExecutionContext.getParameters()))
                .transitions(transitions)
                .status(processExecutionContext.getStatus())
                .creationDate(processExecutionContext.getCreationDate())
                .build();
    }

    private static List<ProcessedObjectTransition> fromTransitionContext(List<TransitionExecution> transitionExecutions) {
        if(ObjectUtils.isEmpty(transitionExecutions)) {
            return Collections.emptyList();
        }
        return transitionExecutions.stream().map(ProcessMapper::fromTransitionContext).toList();

    }
    private static ProcessedObjectTransition fromTransitionContext(TransitionExecution transitionExecution) {
        return ProcessedObjectTransition.builder()
                .fromState(transitionExecution.transition().getFromState().getName())
                .toState(transitionExecution.transition().getToState().getName())
                .priority(transitionExecution.transition().getPriority())
                .pauseAfterTransition(transitionExecution.transition().getPauseAfterTransition())
                .conditions(mapConditions(transitionExecution.transition().getConditions()))
                .action(transitionExecution.transition().getAction().getSimpleName())
                .executionDate(transitionExecution.executionDate())
                .build();
    }

    private static List<String> mapConditions(List<Class<? extends Condition>> conditions) {
        return conditions.stream().map(Class::getSimpleName).toList();
    }
}
