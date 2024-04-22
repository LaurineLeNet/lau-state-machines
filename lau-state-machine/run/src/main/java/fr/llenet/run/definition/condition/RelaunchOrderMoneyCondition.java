package fr.llenet.run.definition.condition;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.core.Condition;
import fr.llenet.run.definition.ParameterNameRun;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RelaunchOrderMoneyCondition implements Condition {
    @Override
    public Boolean isValid(ProcessExecutionContext context) {
        if (context.getParameterValue(ParameterNameRun.RE_ORDER_MONEY.name(), Boolean.class, false) == null) {
            return false;
        }
        return context.getParameterValue(ParameterNameRun.RE_ORDER_MONEY.name(), Boolean.class, false);
    }
}
