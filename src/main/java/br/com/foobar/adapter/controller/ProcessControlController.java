package br.com.foobar.adapter.controller;

import br.com.foobar.adapter.controller.dto.ProcessControlDTO;
import br.com.foobar.domain.ProcessControl;
import br.com.foobar.usecase.processcontrol.CreateProcessControl;
import br.com.foobar.usecase.processcontrol.FindProcessControl;
import br.com.foobar.usecase.processcontrol.UpdateProcessControl;

public class ProcessControlController {

    private final CreateProcessControl createProcessControl;

    private final FindProcessControl findProcessControl;

    private final UpdateProcessControl updateProcessControl;

    public ProcessControlController(final CreateProcessControl createProcessControl,
                                    final FindProcessControl findProcessControl,
                                    final UpdateProcessControl updateProcessControl) {

        this.createProcessControl = createProcessControl;
        this.findProcessControl = findProcessControl;
        this.updateProcessControl = updateProcessControl;

    }

    public Long create(final ProcessControl proccessControl) {
        return this.createProcessControl.create(proccessControl);
    }

    public ProcessControlDTO findProcess(final Long id) {
        return ProcessControlDTO.parseToDto(this.findProcessControl.findProcessControl(id));
    }

    public void update(final ProcessControl proccessControl) {
        this.updateProcessControl.update(proccessControl);
    }

}
