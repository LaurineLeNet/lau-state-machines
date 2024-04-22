package fr.llenet.engine.core;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.exception.ProcessExecutionException;

public interface Action {
    void execute(ProcessExecutionContext context) throws ProcessExecutionException;
}
