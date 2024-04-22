package fr.llenet.engine.core;

import fr.llenet.engine.core.impl.ValidCondition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class State {
    private final String name;
    private final Set<Transition> nextTransitions = new TreeSet<>();
    public static State withName(String name) {
        return new State(name);
    }
    @SafeVarargs
    public final Transition when(Class<? extends Condition>... conditions) {
        Transition transition = new Transition(this, Arrays.asList(conditions));
        nextTransitions.add(transition);
        return transition;
    }

    public Transition then(State nextState) {
        Transition transition = when(ValidCondition.class);
        transition.then(nextState).withPriority(0);
        return transition;
    }

}
