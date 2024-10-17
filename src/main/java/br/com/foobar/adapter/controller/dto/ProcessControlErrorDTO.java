package br.com.foobar.adapter.controller.dto;

import br.com.foobar.domain.ProcessControlError;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProcessControlErrorDTO implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    private String description;

    @JsonIgnore
    private ProcessControlDTO processControlDTO;

    public ProcessControlErrorDTO(final String description) {
        this.description = description;
    }

    public static ProcessControlErrorDTO parseToDto(final ProcessControlError processControlError) {
        return new ProcessControlErrorDTO(processControlError.getDescription());
    }

}
