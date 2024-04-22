package fr.llenet.engine.mapper;

import fr.llenet.engine.parameter.ParameterName;
import fr.llenet.engine.parameter.ParameterValue;

import java.util.Map;
import java.util.stream.Collectors;

public class ParameterMapper {
    public static Map<String, Object> toDatabase(Map<String, ParameterValue<?>> parameters) {
        return parameters.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> ParameterValue.of(entry.getValue().getValue())));
    }
    public static Map<String, ParameterValue<?>> fromDbParameters(Map<String, Object> parameters) {
        return parameters.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (ParameterValue<?>) entry.getValue()));
    }
}
