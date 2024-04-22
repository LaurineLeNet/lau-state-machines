package fr.llenet.lautinystatemachine;

import java.time.Instant;

public class Engine {
    private ProcessExecutionContextDao dao = new ProcessExecutionContextDao();
    public void launch() {
        ProcessDefinition processDefinition = new ProcessDefinition();
        ProcessExecutionContext context = ProcessExecutionContext.builder().creationDate(Instant.now()).build();
        executeTransitions(processDefinition.getStartState(), context);
        dao.save(context);
    }

    private void executeTransitions(State currentState, ProcessExecutionContext context) {
        Transition nextTransition = currentState.getNextTransition();
        while(nextTransition != null) {
            executeAction(nextTransition.getAction());
            context.getTransitions().add(nextTransition);
            context.setLatestStateName(nextTransition.getToState().getName());
            nextTransition = nextTransition.getToState().getNextTransition();
        }
    }

    private void executeAction(Action action) {
        if(action != null) {
            action.run();
        }
    }
}
