package br.com.foobar.application.factory;

import br.com.foobar.adapter.controller.ProcessControlController;
import br.com.foobar.adapter.repository.RepositoryProcessControlImpl;
import br.com.foobar.usecase.processcontrol.CreateProcessControl;
import br.com.foobar.usecase.processcontrol.FindProcessControl;
import br.com.foobar.usecase.processcontrol.UpdateProcessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProcessControlFactory {

    @Autowired
    RepositoryProcessControlImpl repositoryProcessControl;

    @Bean
    public CreateProcessControl createProcess() {
        return new CreateProcessControl(this.repositoryProcessControl);
    }

    @Bean
    public FindProcessControl findProcess() {
        return new FindProcessControl(this.repositoryProcessControl);
    }

    @Bean
    public UpdateProcessControl updateProcess() {
        return new UpdateProcessControl(this.repositoryProcessControl);
    }

    @Bean
    public ProcessControlController userControllerProcess() {
        return new ProcessControlController(createProcess(), findProcess(), updateProcess());
    }
}
