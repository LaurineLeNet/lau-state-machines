package fr.llenet.engine.parameter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ParameterValue<T> {
    private final T value;
    public static <T> ParameterValue<T> of(T value) {
        return new ParameterValue<>(value);
    }
    public String toString() {
        return value.toString();
    }
}
