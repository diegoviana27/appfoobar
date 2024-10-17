package br.com.foobar.usecase.processcontrol;

import br.com.foobar.domain.ProcessControl;
import br.com.foobar.usecase.processcontrol.exception.ProcessControlNotExistsException;
import br.com.foobar.usecase.processcontrol.port.ProcessControlGateway;

public final class UpdateProcessControl {

    private final ProcessControlGateway processControlGateway;

    public UpdateProcessControl(final ProcessControlGateway processControlGateway) {
        this.processControlGateway = processControlGateway;
    }

    public void update(final ProcessControl proccessControl) {

        if (this.processControlGateway.findProcessControl(proccessControl.getId()).isEmpty()) {
            throw new ProcessControlNotExistsException(
                    String.format("Process control with code %s was not found", proccessControl.getId()));
        }

        this.processControlGateway.update(proccessControl);
    }

}
