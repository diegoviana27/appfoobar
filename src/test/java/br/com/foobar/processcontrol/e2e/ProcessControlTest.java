package br.com.foobar.processcontrol.e2e;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
    scripts = {"/insert_process_control.sql"}
)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
final class ProcessControlTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void findProcessControl() throws Exception {
    mockMvc.perform(get("/v1/process/{id}", 155L))
        .andExpect(status().isOk());
  }

  @Test
  @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
      scripts = {"/delete_process_control.sql"}
  )
  void findProcessControlNotExists() throws Exception {
    mockMvc.perform(get("/v1/process/{id}", 155L))
        .andExpect(status().isNoContent());
  }

}
