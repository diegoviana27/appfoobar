package br.com.foobar.usecase.processcontrol.port;

import br.com.foobar.domain.ProcessControl;

import java.util.Optional;

public interface ProcessControlGateway {

    Long create(final ProcessControl proccessControl);

    Optional<ProcessControl> findProcessControl(final Long id);

    void update(final ProcessControl proccessControl);
}
