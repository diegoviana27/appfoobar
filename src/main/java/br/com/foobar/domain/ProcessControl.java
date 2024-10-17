package br.com.foobar.domain;

import java.time.LocalDateTime;
import java.util.Set;

public class ProcessControl {

    private Long id;

    private LocalDateTime initialProcessDate;

    private LocalDateTime terminatedProcessDate;

    private boolean processed;

    private Set<ProcessControlError> errors;

    public ProcessControl(final LocalDateTime initialProcessDate,
                          final LocalDateTime terminatedProcessDate, final Set<ProcessControlError> errors,
                          boolean processed) {

        this.initialProcessDate = initialProcessDate;
        this.terminatedProcessDate = terminatedProcessDate;
        this.errors = errors;
        this.processed = processed;

    }

    public ProcessControl(final Long id, final LocalDateTime initialProcessDate,
                          final LocalDateTime terminatedProcessDate, final Set<ProcessControlError> errors,
                          final boolean processed) {
        this.id = id;
        this.initialProcessDate = initialProcessDate;
        this.terminatedProcessDate = terminatedProcessDate;
        this.errors = errors;
        this.processed = processed;
    }

    public ProcessControl(final Long id) {
        this.id = id;
    }

    public ProcessControl(final LocalDateTime initialProcessDate,
                          final Set<ProcessControlError> errors) {
        this.initialProcessDate = initialProcessDate;
        this.errors = errors;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getInitialProcessDate() {
        return initialProcessDate;
    }

    public LocalDateTime getTerminatedProcessDate() {
        return terminatedProcessDate;
    }

    public void setTerminatedProcessDate(LocalDateTime terminatedProcessDate) {
        this.terminatedProcessDate = terminatedProcessDate;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public Set<ProcessControlError> getErrors() {
        return errors;
    }

}
