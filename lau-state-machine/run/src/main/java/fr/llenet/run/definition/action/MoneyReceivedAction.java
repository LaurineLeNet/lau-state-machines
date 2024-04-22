package fr.llenet.run.definition.action;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.core.Action;
import fr.llenet.engine.parameter.ParameterName;
import fr.llenet.engine.parameter.ParameterValue;
import fr.llenet.run.definition.ParameterNameRun;
import fr.llenet.run.model.FallenToothOrder;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MoneyReceivedAction implements Action {
    @Override
    public void execute(ProcessExecutionContext context) {
        log.info("Money received");

        Integer moneyAmount = context.getParameterValue(ParameterNameRun.MONEY_AMOUNT.name(), Integer.class, true);
        FallenToothOrder order = context.getParameterValue(ParameterName.PROCESSED_OBJECT.name(), FallenToothOrder.class, true);
        order.setMoneyAmount(moneyAmount);
        context.addParameter(ParameterName.PROCESSED_OBJECT.name(), ParameterValue.of(order));

    }
}
