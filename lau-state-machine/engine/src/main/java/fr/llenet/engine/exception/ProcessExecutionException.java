package fr.llenet.engine.exception;

public class ProcessExecutionException extends Exception {
    private ProcessExecutionException(ProcessErrorCode errorCode, Object... args) {
        super(String.format(errorCode.getMessage(), args));
    }
    public static ProcessExecutionException of(ProcessErrorCode errorCode, Object... args) {
        return new ProcessExecutionException(errorCode, args);
    }
}
