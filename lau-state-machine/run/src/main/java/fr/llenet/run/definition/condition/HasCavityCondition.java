package fr.llenet.run.definition.condition;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.core.Condition;
import fr.llenet.engine.parameter.ParameterName;
import fr.llenet.run.model.FallenToothOrder;
import org.springframework.stereotype.Component;

@Component
public class HasCavityCondition implements Condition {
    @Override
    public Boolean isValid(ProcessExecutionContext context) {
        FallenToothOrder order = context.getParameterValue(ParameterName.PROCESSED_OBJECT.name(), FallenToothOrder.class, true);
        return order.getHasCavity();
    }
}
