package fr.llenet.engine.helper;

import fr.llenet.engine.core.Action;
import fr.llenet.engine.core.Condition;
import fr.llenet.engine.core.PostAction;
import fr.llenet.engine.core.Transition;
import fr.llenet.engine.core.impl.ValidCondition;
import fr.llenet.engine.core.impl.VoidAction;
import fr.llenet.engine.definition.ProcessDefinition;
import fr.llenet.engine.definition.ProcessType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessExecutionHelper {
    private final Map<Class<? extends ProcessDefinition>, ProcessDefinition> processDefinitions = new HashMap<>();
    private final Map<Class<? extends Condition>, Condition> conditionsImplementation = new HashMap<>();
    private final Map<Class<? extends Action>, Action> actionsImplementation = new HashMap<>();
    private final Map<Class<? extends PostAction>, PostAction> postActionsImplementation = new HashMap<>();

    public ProcessExecutionHelper() {
        conditionsImplementation.put(ValidCondition.class, new ValidCondition());
        actionsImplementation.put(VoidAction.class, new VoidAction());
    }

    /* ---- Register ---- */

    public <T extends ProcessDefinition> void registerDefinitionImplementation(T child) {
        processDefinitions.put(child.getClass(), child);
    }

    public <T extends Condition> void registerConditionImplementation(List<T> children) {
        children.forEach(c -> conditionsImplementation.put(c.getClass(), c));
    }

    public <T extends Action> void registerActionImplementation(List<T> children) {
        children.forEach(c -> actionsImplementation.put(c.getClass(), c));
    }
    public <T extends PostAction> void registerPostActionImplementation(List<T> children) {
        children.forEach(c -> postActionsImplementation.put(c.getClass(), c));
    }

    /* ---- Helper methods ----*/
    public ProcessDefinition definitionFromType(ProcessType type) {
        return processDefinitions.get(type.processDefinition());
    }

    public List<Condition> conditionsFromTransition(Transition transition) {
        return transition.getConditions().stream()
                .map(conditionsImplementation::get)
                .toList();
    }

    public Action actionFromTransition(Transition transition) {
        return actionsImplementation.get(transition.getAction());
    }

    public List<PostAction> postActionFromTransition(Transition transition) {
        return transition.getPostActions().stream()
                .map(postActionsImplementation::get)
                .toList();
    }
}
