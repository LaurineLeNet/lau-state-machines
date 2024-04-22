package fr.llenet.run.definition.action;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.core.Action;
import fr.llenet.engine.exception.ProcessExecutionException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class OrderBigMoney implements Action {
    @Override
    public void execute(ProcessExecutionContext context) throws ProcessExecutionException {
        log.info("Order big money");
    }
}
