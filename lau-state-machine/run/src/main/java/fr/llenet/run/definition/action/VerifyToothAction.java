package fr.llenet.run.definition.action;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.core.Action;
import fr.llenet.engine.parameter.ParameterName;
import fr.llenet.engine.parameter.ParameterValue;
import fr.llenet.run.model.FallenToothOrder;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class VerifyToothAction implements Action {
    @Override
    public void execute(ProcessExecutionContext context) {
        log.info("Tooth verification...");

        FallenToothOrder order = context.getParameterValue(ParameterName.PROCESSED_OBJECT.name(), FallenToothOrder.class, true);
        order.setHasCavity(order.getChildName().length() <= 5);
        log.info("Tooth has cavity: {}", order.getHasCavity());
        context.addParameter(ParameterName.PROCESSED_OBJECT.name(), ParameterValue.of(order));
    }
}
