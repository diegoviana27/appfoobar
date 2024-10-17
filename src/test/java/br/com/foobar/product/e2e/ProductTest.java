package br.com.foobar.product.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.foobar.application.service.QueueSender;
import br.com.foobar.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
    scripts = {"/insert_products.sql"}
)
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
    scripts = {"/delete_products.sql"}
)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
final class ProductTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private QueueSender queueSender;

  @Test
  void saveProductsFromFile() throws Exception {

    File fileProducts = new File(getClass().getClassLoader().getResource("products.csv").getFile());

    when(this.queueSender.send(Files.readAllBytes(fileProducts.toPath())))
        .thenReturn(1L);

    MockMultipartFile file
        = new MockMultipartFile(
        "file",
        fileProducts.getName(),
        "text/csv",
        Files.readAllBytes(fileProducts.toPath())
    );

    MvcResult result = mockMvc.perform(multipart("/v1/products/").file(file))
        .andExpect(status().isAccepted()).andReturn();

    assertEquals("{\"tracking code\":\"" + 1L + "\"}", result.getResponse().getContentAsString());

  }

  @Test
  void saveProductsFromFileWithTypeError() throws Exception {

    File fileProducts = new File(
        getClass().getClassLoader().getResource("products_with_error_type.txt").getFile());

    MockMultipartFile file
        = new MockMultipartFile(
        "file",
        fileProducts.getName(),
        "text/plain",
        Files.readAllBytes(fileProducts.toPath())
    );

    mockMvc.perform(multipart("/v1/products/").file(file))
        .andExpect(status().isBadRequest());

  }

  @Test
  void findProduct() throws Exception {
    mockMvc.perform(get("/v1/products/{lm}", 2L))
        .andExpect(status().isOk());
  }

  @Test
  void findProductNotExists() throws Exception {
    mockMvc.perform(get("/v1/products/{lm}", 5444L))
        .andExpect(status().isNoContent());
  }

  @Test
  void findAllProduct() throws Exception {
    mockMvc.perform(get("/v1/products"))
        .andExpect(status().isOk());
  }

  @Test
  @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
      scripts = {"/delete_products.sql"}
  )
  void findAllProductsEmpty() throws Exception {
    mockMvc.perform(get("/v1/products"))
        .andExpect(status().isNoContent());
  }

  @Test
  void updateProduct() throws Exception {

    Product product = new Product(1L, "Name updated",
        false, "Description update", BigDecimal.valueOf(50.00), 2L);

    mockMvc.perform(put("/v1/products")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isOk());

  }

  @Test
  void updateProductNotExists() throws Exception {

    Product product = new Product(10000L, "Name updated",
        false, "Description update", BigDecimal.valueOf(50.00), 2L);

    mockMvc.perform(put("/v1/products")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isNoContent());

  }

  @Test
  void updateProductWithPriceZeroLessThanZero() throws Exception {

    Product product = new Product(10000L, "Name updated",
        false, "Description update", BigDecimal.valueOf(-50.00), 2L);

    mockMvc.perform(put("/v1/products")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isConflict());

  }

  @Test
  void updateProductValidate() throws Exception {

    Product product = new Product(null, "Name updated",
        false, "Description update", BigDecimal.valueOf(-50.00), 2L);

    mockMvc.perform(put("/v1/products")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isConflict());

  }

  @Test
  void updateProductConstraintViolation() throws Exception {

    Product product = new Product(1L,
        "ConstraintError ConstraintError ConstraintError ConstraintError"
            + "ConstraintError ConstraintError ConstraintError ConstraintError"
            + "ConstraintError ConstraintError ConstraintError ConstraintError"
            + "ConstraintError ConstraintError ConstraintError ConstraintError"
            + "ConstraintError ConstraintError ConstraintError ConstraintError"
            + "ConstraintError ConstraintError ConstraintError ConstraintError"
            + "ConstraintError ConstraintError ConstraintError ConstraintError"
            + "ConstraintError ConstraintError ConstraintError ConstraintError",
        false, "Description update", BigDecimal.valueOf(50.00), 2L);

    mockMvc.perform(put("/v1/products")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isConflict());

  }

  @Test
  void deleteProduct() throws Exception {
    mockMvc.perform(delete("/v1/products/{lm}", 1L))
        .andExpect(status().isOk());
  }

  @Test
  void deleteProductNotExists() throws Exception {
    mockMvc.perform(delete("/v1/products/{lm}", 5999L))
        .andExpect(status().isNoContent());
  }

}
