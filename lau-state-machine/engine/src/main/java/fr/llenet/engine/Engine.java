package fr.llenet.engine;

import fr.llenet.engine.context.ProcessExecutionContext;
import fr.llenet.engine.definition.ProcessType;
import fr.llenet.engine.parameter.ParameterName;
import fr.llenet.engine.parameter.ParameterValue;

import java.util.Map;

public interface Engine {
    ProcessExecutionContext launch(ProcessType processType, Map<String, ParameterValue<?>> params);
    ProcessExecutionContext relaunch(ProcessType processType, Map<String, ParameterValue<?>> params, String id);
}
