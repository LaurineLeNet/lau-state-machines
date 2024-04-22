package fr.llenet.engine.exception;

public class ImpactFreeProcessExecutionException extends RuntimeException {
    private ImpactFreeProcessExecutionException(ImpactFreeProcessErrorCode errorCode, Object... args) {
        super(String.format(errorCode.getMessage(), args));
    }
    public static ImpactFreeProcessExecutionException of(ImpactFreeProcessErrorCode errorCode, Object... args) {
        return new ImpactFreeProcessExecutionException(errorCode, args);
    }
}
