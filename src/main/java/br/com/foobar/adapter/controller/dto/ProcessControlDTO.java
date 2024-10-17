package br.com.foobar.adapter.controller.dto;

import br.com.foobar.domain.ProcessControl;
import br.com.foobar.domain.ProcessControlError;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProcessControlDTO implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private Long id;

    private LocalDateTime initialProcessDate;

    private LocalDateTime terminatedProcessDate;

    private boolean processed;

    private Set<ProcessControlErrorDTO> errors;

    @JsonIgnore
    private byte[] file;

    public ProcessControlDTO(final Long id, final LocalDateTime initialProcessDate,
                             final byte[] file) {

        this.id = id;
        this.initialProcessDate = initialProcessDate;
        this.file = file;

    }

    public ProcessControlDTO(final Long id,
                             final LocalDateTime initialProcessDate, final LocalDateTime terminatedProcessDate,
                             final Set<ProcessControlErrorDTO> errors,
                             final boolean processed) {

        this.id = id;
        this.initialProcessDate = initialProcessDate;
        this.terminatedProcessDate = terminatedProcessDate;
        this.errors = errors;
        this.processed = processed;

    }

    public static ProcessControlDTO parseToDto(final ProcessControl proccessControl) {

        Set<ProcessControlErrorDTO> errors = proccessControl.getErrors().stream()
                .map(ProcessControlErrorDTO::parseToDto).collect(
                        Collectors.toSet());

        return new ProcessControlDTO(proccessControl.getId(), proccessControl.getInitialProcessDate(),
                proccessControl.getTerminatedProcessDate(),
                errors,
                proccessControl.isProcessed());
    }

    public static ProcessControl parseFromDto(final ProcessControlDTO processControlDTO) {

        Set<ProcessControlError> errors = processControlDTO.getErrors().stream()
                .map(error -> new ProcessControlError(error.getDescription())).collect(
                        Collectors.toSet());

        return new ProcessControl(processControlDTO.getId(), processControlDTO.getInitialProcessDate(),
                processControlDTO.getTerminatedProcessDate(), errors,
                processControlDTO.isProcessed());
    }
}
