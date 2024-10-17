package br.com.foobar.usecase.processcontrol;

import br.com.foobar.domain.ProcessControl;
import br.com.foobar.usecase.processcontrol.exception.ProcessControlNotExistsException;
import br.com.foobar.usecase.processcontrol.port.ProcessControlGateway;

public final class FindProcessControl {

    private final ProcessControlGateway processControlGateway;

    public FindProcessControl(final ProcessControlGateway processControlGateway) {
        this.processControlGateway = processControlGateway;
    }

    public ProcessControl findProcessControl(final Long id) {
        return this.processControlGateway.findProcessControl(id)
                .orElseThrow(() -> new ProcessControlNotExistsException(
                        String.format("Process control with code %s was not found", id)));
    }
}