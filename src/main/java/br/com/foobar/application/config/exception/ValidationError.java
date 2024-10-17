package br.com.foobar.application.config.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError {

    private final List<String> globalErrors = new ArrayList<>();

    public ValidationError(String error) {
        this.globalErrors.add(error);
    }

    public void addGlobalErrors(final String error) {
        globalErrors.add(error);
    }

    public List<String> getGlobalErrors() {
        return globalErrors;
    }

}
