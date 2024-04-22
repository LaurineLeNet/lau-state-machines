package fr.llenet.engine.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImpactFreeProcessErrorCode {
    ERROR_OCCURRED("An error occurred at our end"),
    PARAMETER_NOT_FOUND("Parameter not found"),

        ;
    private final String message;
}
