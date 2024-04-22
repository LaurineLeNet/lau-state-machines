package fr.llenet.engine.context;

import fr.llenet.engine.core.Transition;

import java.time.Instant;

public record TransitionExecution(Transition transition, Instant executionDate) {
}


