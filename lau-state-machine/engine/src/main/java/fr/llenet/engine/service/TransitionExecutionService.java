package fr.llenet.engine.service;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.context.ProcessStatus;
import fr.llenet.engine.context.TransitionExecution;
import fr.llenet.engine.core.Action;
import fr.llenet.engine.core.PostAction;
import fr.llenet.engine.core.Transition;
import fr.llenet.engine.exception.ProcessExecutionException;
import fr.llenet.engine.helper.ProcessExecutionHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class TransitionExecutionService {
    private final ProcessExecutionHelper processExecutionHelper;

    public void execute(ProcessExecutionContext processExecutionContext, Transition transition) {
        executeAction(processExecutionContext, transition);
        executePostAction(processExecutionContext, transition);
    }

    private void executeAction(ProcessExecutionContext processExecutionContext, Transition transition) {
        Action action = processExecutionHelper.actionFromTransition(transition);
        try {
            action.execute(processExecutionContext);
        } catch (ProcessExecutionException e) {
            log.error("Error when executing transition {}", transition);
            processExecutionContext.setStatus(ProcessStatus.FAILED);
        }
    }
    private void executePostAction(ProcessExecutionContext processExecutionContext, Transition transition) {
        List<PostAction> postActions = processExecutionHelper.postActionFromTransition(transition);
        postActions.forEach((postAction)-> {
            try {
                postAction.execute(processExecutionContext);
            } catch (ProcessExecutionException e) {
                log.error("Error when executing transition {}", transition);
                processExecutionContext.setStatus(ProcessStatus.FAILED);
            }
        });
    }
}
