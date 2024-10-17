package br.com.foobar.domain;

public class ProcessControlError {

    private final String description;

    public ProcessControlError(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
