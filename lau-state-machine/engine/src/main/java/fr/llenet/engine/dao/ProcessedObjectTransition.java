package fr.llenet.engine.dao;

import lombok.Builder;

import java.time.Instant;
import java.util.List;
@Builder
public record ProcessedObjectTransition(String fromState,
                                        String toState,
                                        Integer priority,
                                        Boolean pauseAfterTransition,
                                        List<String> conditions,
                                        String action,
                                        Instant executionDate) {
}
