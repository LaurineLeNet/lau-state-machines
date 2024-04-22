package fr.llenet.engine.definition;

import fr.llenet.engine.core.State;
import fr.llenet.engine.core.Transition;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.nonNull;

public abstract class ProcessDefinition {
    protected final State startState = State.withName("startState");
    private final List<State> states = new ArrayList<>();
    public State startState() {
        return startState;
    }
    public List<State> states() {
        return unmodifiableList(states);
    }
    public abstract ProcessedObject initProcessedObject();

    public ProcessDefinition() {
        initProcessingDefinition();
        initializeStates(startState.getNextTransitions());
    }
    protected abstract void initProcessingDefinition();

    private void initializeStates(Set<Transition> transitions) {
        transitions.forEach(transition -> {
            if (states.contains(transition.getToState())) {
                return;
            }
            if (nonNull(transition.getToState())) {
                states.add(transition.getToState());
            }
            if (ObjectUtils.isNotEmpty(transition.getToState().getNextTransitions())) {
                initializeStates(transition.getToState().getNextTransitions());
            }
        });
    }
}
