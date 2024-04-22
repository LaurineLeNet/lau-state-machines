package fr.llenet.engine.context;

import fr.llenet.engine.core.Transition;
import fr.llenet.engine.exception.ImpactFreeProcessErrorCode;
import fr.llenet.engine.exception.ImpactFreeProcessExecutionException;
import fr.llenet.engine.parameter.ParameterHelper;
import fr.llenet.engine.parameter.ParameterValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
public class ProcessExecutionContext {
    private String executionId;
    private ProcessStatus status;
    private Transition currentTransition;
    private final List<TransitionExecution> transitionExecutions = new ArrayList<>();
    private Instant creationDate;
    private Map<String, ParameterValue<?>> parameters;


    private final ParameterHelper parameterHelper = new ParameterHelper();
    public <T> T getParameterValue(String parameterName, Class<T> parameterType, boolean throwIfNotFound) {
        return parameterHelper.getValue(parameters, parameterName, parameterType).orElseGet(
                () -> {
                    if(throwIfNotFound) {
                        throw ImpactFreeProcessExecutionException.of(ImpactFreeProcessErrorCode.PARAMETER_NOT_FOUND);
                    }
                    else {
                        return null;
                    }
                }
        );
    }
    public void addParameters(Map<String, ParameterValue<?>> inputParameters) {
        parameters.putAll(inputParameters);
    }

    public void addParameter(String name, ParameterValue<?> value) {
        parameters.put(name, value);
    }
}
