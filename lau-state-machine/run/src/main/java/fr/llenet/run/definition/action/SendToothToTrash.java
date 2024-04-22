package fr.llenet.run.definition.action;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.core.Action;
import fr.llenet.run.dao.ToothInventoryRepository;
import fr.llenet.run.definition.ParameterNameRun;
import fr.llenet.run.model.ToothInventory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@Log4j2
@RequiredArgsConstructor
public class SendToothToTrash implements Action {
    private final ToothInventoryRepository repository;

    @Override
    public void execute(ProcessExecutionContext context) {
        log.info("Put tooth to trash");
        String inventoryId = context.getParameterValue(ParameterNameRun.INVENTORY_ID.name(), String.class, true);
        ToothInventory toothInventory = repository.findById(inventoryId)
                .orElseThrow(() -> new NoSuchElementException("No inventory with id " + inventoryId));
        toothInventory.setTeethInTrash(toothInventory.getTeethInTrash() + 1);
        repository.save(toothInventory);
    }
}
