package fr.llenet.engine.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProcessErrorCode {
    ERROR_OCCURRED("An error occurred at our end")
        ;
    private final String message;
}
