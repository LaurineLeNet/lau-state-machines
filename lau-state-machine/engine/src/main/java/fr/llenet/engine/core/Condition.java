package fr.llenet.engine.core;

import fr.llenet.engine.context.ProcessExecutionContext;

public interface Condition {
    Boolean isValid(ProcessExecutionContext context);
}
