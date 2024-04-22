package fr.llenet.run.definition.condition;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.core.Condition;
import fr.llenet.run.definition.ParameterNameRun;
import org.springframework.stereotype.Component;

@Component
public class MoneyReceivedCondition implements Condition {
    @Override
    public Boolean isValid(ProcessExecutionContext context) {
        return context.getParameterValue(ParameterNameRun.MONEY_AMOUNT.name(), Integer.class, false) != null;
    }
}
