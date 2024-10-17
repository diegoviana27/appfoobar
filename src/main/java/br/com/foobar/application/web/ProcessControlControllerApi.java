package br.com.foobar.application.web;

import br.com.foobar.adapter.controller.ProcessControlController;
import br.com.foobar.adapter.controller.dto.ProcessControlDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/process")
@Tag(name = "Process Resources API")
public class ProcessControlControllerApi {

    private final ProcessControlController processControlController;

    public ProcessControlControllerApi(
            final br.com.foobar.adapter.controller.ProcessControlController processControlController) {
        this.processControlController = processControlController;
    }

    @Operation(summary = "Find process")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Process found"),
            @ApiResponse(responseCode = "204", description = "Process not found")
    })
    @GetMapping("{id}")
    public ResponseEntity<ProcessControlDTO> findProcess(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(this.processControlController.findProcess(id), HttpStatus.OK);
    }

}
