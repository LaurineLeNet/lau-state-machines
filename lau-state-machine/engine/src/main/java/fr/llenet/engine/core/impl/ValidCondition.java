package fr.llenet.engine.core.impl;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.core.Condition;

public class ValidCondition implements Condition {
    @Override
    public Boolean isValid(ProcessExecutionContext context) {
        return true;
    }
}
