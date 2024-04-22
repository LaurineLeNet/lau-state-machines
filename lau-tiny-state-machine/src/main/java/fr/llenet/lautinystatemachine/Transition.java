package fr.llenet.lautinystatemachine;

import lombok.Getter;

@Getter
public class Transition {
    private State fromState;
    private State toState;
    private Action action;

    public Transition(State fromState, State toState) {
        this.fromState = fromState;
        this.toState = toState;
    }

    public Transition withAction(Action action) {
        this.action = action;
        return this;
    }
}
