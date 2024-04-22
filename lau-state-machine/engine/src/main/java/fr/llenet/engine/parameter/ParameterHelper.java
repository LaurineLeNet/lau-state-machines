package fr.llenet.engine.parameter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
@RequiredArgsConstructor
public class ParameterHelper {
    private final ObjectMapper mapper = new ObjectMapper();
    public <T> Optional<T> getValue(Map<String, ParameterValue<?>> parameters, String name, Class<T> parameterType) {
        if(Objects.nonNull(parameters.get(name)) && Objects.nonNull(parameters.get(name).getValue())) {
            return Optional.of(mapper.convertValue(parameters.get(name).getValue(), parameterType));
        }
        return Optional.empty();
    }
}
