package fr.llenet.lautinystatemachine;

import lombok.Getter;

@Getter
public class State {
    private String name;
    private Transition nextTransition;

    private State(String name) {
        this.name = name;
    }

    public static State withName(String name) {
        return new State(name);
    }

    public Transition then(State nextState) {
        this.nextTransition = new Transition(this, nextState);
        return this.nextTransition;
    }
}
