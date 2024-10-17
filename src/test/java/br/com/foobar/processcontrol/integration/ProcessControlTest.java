package br.com.foobar.processcontrol.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.foobar.adapter.controller.ProcessControlController;
import br.com.foobar.adapter.controller.dto.ProcessControlDTO;
import br.com.foobar.application.factory.ProcessControlFactory;
import br.com.foobar.domain.ProcessControl;
import br.com.foobar.domain.ProcessControlError;
import br.com.foobar.usecase.processcontrol.exception.ProcessControlNotExistsException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
    scripts = {"/insert_process_control.sql"}
)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
final class ProcessControlTest {

  @Autowired
  private ProcessControlFactory useCase;

  private static ProcessControl buildProcessControlWithErrors() {

    Long id = 155L;

    LocalDateTime localDateTimeInitialProcessDate = LocalDateTime.of(2022, 9, 7, 17, 46, 37);
    LocalDateTime localDateTimeTerminatedProcessDate = LocalDateTime.of(2022, 9, 7, 18, 0, 40);

    return new ProcessControl(id,
        localDateTimeInitialProcessDate, localDateTimeTerminatedProcessDate,
        new HashSet<>(), true);

  }

  @Test
  void createNewProcessControl() {

    ProcessControl processControl = new ProcessControl(
        LocalDateTime.now(), null,
        new HashSet<>(), true);

    Long codeTracking = this.useCase.userControllerProcess().create(processControl);

    assertNotNull(codeTracking);
    assertEquals(1L, codeTracking);

  }

  @Test
  void createProcessControlWithErrors() {

    ProcessControl processControlWithErrors = buildProcessControlWithErrors();

    Set<ProcessControlError> errors = new HashSet<>();
    errors.add(new ProcessControlError("Error Teste 1"));
    errors.add(new ProcessControlError("Error Teste 2"));
    errors.add(new ProcessControlError("Error Teste 2"));

    processControlWithErrors.getErrors().addAll(errors);

    this.useCase.userControllerProcess().update(processControlWithErrors);

    ProcessControl processControl = this.useCase.findProcess().findProcessControl(155L);

    assertNotNull(processControl);
    assertEquals(3, processControl.getErrors().size());
    assertTrue(processControl.isProcessed());
    assertNotNull(processControl.getInitialProcessDate());
    assertNotNull(processControl.getTerminatedProcessDate());

  }

  @Test
  @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
      scripts = {"/delete_process_control.sql"}
  )
  void createConstraintViolationException() {

    ProcessControl processControl = new ProcessControl(
        null, null,
        new HashSet<>(), true);

    ProcessControlController processControlController = this.useCase.userControllerProcess();

    assertThrows(ConstraintViolationException.class,
        () -> processControlController.create(processControl));
  }

  @Test
  void findProcessControlWithErros() {

    ProcessControl processControl = buildProcessControlWithErrors();

    Set<ProcessControlError> errors = new HashSet<>();
    errors.add(new ProcessControlError("Error Teste 1"));
    processControl.getErrors().addAll(errors);

    this.useCase.createProcess().create(processControl);

    ProcessControl processControlFound = this.useCase.findProcess()
        .findProcessControl(processControl.getId());

    assertNotNull(processControlFound);
    assertTrue(processControl.isProcessed());
    assertNotNull(processControl.getInitialProcessDate());
    assertNotNull(processControl.getTerminatedProcessDate());
    assertEquals(1, processControlFound.getErrors().size());

  }

  @Test
  void updateProcessControl() {

    Long id = 155L;
    LocalDateTime localDateTime = LocalDateTime.now();

    ProcessControl processControl = this.useCase.findProcess().findProcessControl(id);
    processControl.setTerminatedProcessDate(localDateTime);
    processControl.setProcessed(true);

    this.useCase.userControllerProcess().update(processControl);

    ProcessControl processControlUpdated = ProcessControlDTO.parseFromDto(
        this.useCase.userControllerProcess().findProcess(155L));

    assertNotNull(processControlUpdated);
    assertNotNull(processControlUpdated.getTerminatedProcessDate());
    assertEquals(155L, processControlUpdated.getId());
    assertEquals(processControl.getInitialProcessDate(),
        processControlUpdated.getInitialProcessDate());
    assertTrue(processControlUpdated.isProcessed());

  }

  @Test
  void updateProcessControlNotExists() {

    Long id = 158L;
    LocalDateTime localDateTime = LocalDateTime.now();

    ProcessControl processControl = new ProcessControl(id);
    processControl.setTerminatedProcessDate(localDateTime);
    processControl.setProcessed(true);

    ProcessControlController processControlController = this.useCase.userControllerProcess();

    assertThrows(ProcessControlNotExistsException.class,
        () -> processControlController.update(processControl));

  }

  @Test
  void findProcessControl() {

    Long id = 156L;
    LocalDateTime localDateTimeInitialProcessDate = LocalDateTime.of(2022, 9, 7, 17, 46, 37);
    LocalDateTime localDateTimeTerminatedProcessDate = LocalDateTime.of(2022, 9, 7, 18, 0, 40);

    ProcessControl processControl = this.useCase.findProcess().findProcessControl(id);

    assertNotNull(processControl);
    assertEquals(id, processControl.getId());
    assertEquals(localDateTimeInitialProcessDate, processControl.getInitialProcessDate());
    assertEquals(localDateTimeTerminatedProcessDate, processControl.getTerminatedProcessDate());
    assertTrue(processControl.isProcessed());


  }

  @Test
  void findProcessControlNotExists() {

    ProcessControlController processControlController = this.useCase.userControllerProcess();

    assertThrows(ProcessControlNotExistsException.class,
        () -> processControlController.findProcess(158L));
  }

}
