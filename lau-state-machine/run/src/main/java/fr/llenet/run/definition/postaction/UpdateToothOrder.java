package fr.llenet.run.definition.postaction;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.core.PostAction;
import fr.llenet.engine.exception.ProcessExecutionException;
import fr.llenet.engine.parameter.ParameterName;
import fr.llenet.engine.parameter.ParameterValue;
import fr.llenet.run.dao.FallenToothRepository;
import fr.llenet.run.model.FallenToothOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class UpdateToothOrder implements PostAction {
    private final FallenToothRepository repository;
    @Override
    public void execute(ProcessExecutionContext context) {
        FallenToothOrder order = context.getParameterValue(ParameterName.PROCESSED_OBJECT.name(), FallenToothOrder.class, true);
        log.info("Updating order with id: {}", order.getId());
        FallenToothOrder savedOrder = repository.save(order);
        context.addParameter(ParameterName.PROCESSED_OBJECT.name(), ParameterValue.of(savedOrder));
        log.info("Order updated");

    }
}
