package br.com.foobar.usecase.processcontrol.exception;

public class ProcessControlNotExistsException extends RuntimeException {

    public ProcessControlNotExistsException(final String message) {
        super(message);
    }

}
