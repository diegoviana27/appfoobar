package br.com.foobar.usecase.processcontrol;

import br.com.foobar.domain.ProcessControl;
import br.com.foobar.usecase.processcontrol.port.ProcessControlGateway;

public final class CreateProcessControl {

    private final ProcessControlGateway processControlGateway;

    public CreateProcessControl(final ProcessControlGateway processControlGateway) {
        this.processControlGateway = processControlGateway;
    }

    public Long create(final ProcessControl proccessControl) {
        return this.processControlGateway.create(proccessControl);
    }

}
