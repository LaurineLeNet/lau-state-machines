package fr.llenet.run.controller;

import fr.llenet.engine.Engine;
import fr.llenet.engine.parameter.ParameterValue;
import fr.llenet.run.dao.ToothInventoryRepository;
import fr.llenet.run.definition.ParameterNameRun;
import fr.llenet.run.definition.ProcessTypeImpl;
import fr.llenet.run.dto.RelaunchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class FallenToothController {
    private final Engine engine;
    private final ToothInventoryRepository inventoryRepository;

    @PostMapping("fallenTooth")
    public String onFallenTooth(@RequestBody String name) {
        Map<String, ParameterValue<?>> parameters = new HashMap<>();

        parameters.put(ParameterNameRun.CHILD_NAME.name(), ParameterValue.of(name));
        parameters.putIfAbsent(ParameterNameRun.INVENTORY_ID.name(), ParameterValue.of(inventoryRepository.TOOTH_REPOSITORY_ID));

        return engine.launch(ProcessTypeImpl.CHILD, parameters).getExecutionId();
    }

    @PostMapping("onMoneyReceived")
    public void onMoneyReceived(@RequestBody RelaunchDto relaunchDto) {
        Map<String, ParameterValue<?>> params = new HashMap<>();
        params.put(ParameterNameRun.MONEY_AMOUNT.name(), ParameterValue.of(relaunchDto.amount()));
        engine.relaunch(ProcessTypeImpl.CHILD, params, relaunchDto.id());
    }

    @PostMapping("relaunchOrderMoney/{id}")
    public void relaunchOrderMoney(@PathVariable String id) {
        Map<String, ParameterValue<?>> parameters = new HashMap<>();
        parameters.put(ParameterNameRun.RE_ORDER_MONEY.name(), ParameterValue.of(true));
        engine.relaunch(ProcessTypeImpl.CHILD, parameters, id);
    }

}
