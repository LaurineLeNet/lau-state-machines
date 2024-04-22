package fr.llenet.engine.core;

import fr.llenet.engine.core.impl.VoidAction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Transition implements Comparable<Transition>{
    private final State fromState;
    private State toState;
    private int priority;
    private Boolean pauseAfterTransition = false;
    private final List<Class<? extends Condition>> conditions;
    private Class<? extends Action> action = VoidAction.class;
    private List<Class<? extends PostAction>> postActions = new ArrayList<>();

    @Override
    public int compareTo(Transition o) {
        return o.priority - priority;
    }
    public Boolean isProcessPaused() {
        return pauseAfterTransition;
    }
    public Transition then(State state) {
        toState = state;
        return this;
    }
    public Transition withPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public Transition withAction(Class<? extends Action> action) {
        this.action = action;
        return this;
    }
    public Transition withPauseAfterTransition() {
        pauseAfterTransition = true;
        return this;
    }
    @SafeVarargs
    public final Transition withPostActions(Class<? extends PostAction>... postActions) {
        this.postActions = Arrays.asList(postActions);
        return this;
    }
}
