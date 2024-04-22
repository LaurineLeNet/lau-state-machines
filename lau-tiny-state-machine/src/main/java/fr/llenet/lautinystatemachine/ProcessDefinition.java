package fr.llenet.lautinystatemachine;

import lombok.Getter;

@Getter
public class ProcessDefinition {
    private final State startState = State.withName(StateName.START_STATE.name());

    private void initProcessingDefinition() {
        State toothReceived = State.withName(StateName.TOOTH_RECEIVED.name());
        State toothVerified = State.withName(StateName.TOOTH_VERIFIED.name());

        startState.then(toothReceived);
        toothReceived.then(toothVerified).withAction(new CheckCavityAction());
    }

    public ProcessDefinition() {
        initProcessingDefinition();
    }
}
enum StateName {
    START_STATE, TOOTH_RECEIVED, TOOTH_VERIFIED
}
