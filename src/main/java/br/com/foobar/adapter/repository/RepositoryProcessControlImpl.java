package br.com.foobar.adapter.repository;

import br.com.foobar.adapter.repository.model.ProcessControlModel;
import br.com.foobar.domain.ProcessControl;
import br.com.foobar.usecase.processcontrol.port.ProcessControlGateway;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Component
public class RepositoryProcessControlImpl implements ProcessControlGateway {

    private final RepositoryProcessControl repositoryProcessControl;

    public RepositoryProcessControlImpl(
            @Lazy final RepositoryProcessControl repositoryProcessControl) {
        this.repositoryProcessControl = repositoryProcessControl;
    }

    @Override
    public Long create(final ProcessControl proccessControl) {
        return this.repositoryProcessControl.save(ProcessControlModel.parseToModel(proccessControl))
                .getId();
    }

    @Override
    public Optional<ProcessControl> findProcessControl(final Long id) {

        Optional<ProcessControlModel> processControlModel = this.repositoryProcessControl.findById(id);

        return processControlModel.map(ProcessControlModel::parseFromModel);

    }

    @Override
    public void update(final ProcessControl proccessControl) {
        this.repositoryProcessControl.save(ProcessControlModel.parseToModel(proccessControl));
    }

}
