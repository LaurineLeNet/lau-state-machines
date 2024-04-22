package fr.llenet.run.definition.action;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.core.Action;
import fr.llenet.engine.parameter.ParameterValue;
import fr.llenet.run.definition.ParameterNameRun;
import fr.llenet.run.model.ShippingStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CompleteOrderAction implements Action {
    @Override
    public void execute(ProcessExecutionContext context) {
        log.info("Order complete !!");
        context.addParameter(ParameterNameRun.SHIPPING_STATUS.name(), ParameterValue.of(ShippingStatus.SHIPPED));
    }
}
