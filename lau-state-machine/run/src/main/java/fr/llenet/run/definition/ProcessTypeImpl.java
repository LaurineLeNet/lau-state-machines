package fr.llenet.run.definition;

import fr.llenet.engine.definition.ProcessDefinition;
import fr.llenet.engine.definition.ProcessType;

public enum ProcessTypeImpl implements ProcessType {
    CHILD(ChildMouseDefinition.class);
    private final Class<? extends ProcessDefinition> processDefinition;
    ProcessTypeImpl(Class<? extends ProcessDefinition> processDefinition) {
        this.processDefinition = processDefinition;
    }
    @Override
    public Class<? extends ProcessDefinition> processDefinition() {
        return processDefinition;
    }
}
