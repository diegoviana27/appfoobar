package br.com.foobar.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.foobar.adapter.controller.dto.ProductDTO;
import br.com.foobar.application.helper.HelperCSV;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class HelperCSVTest {

  @Test
  void loadFileTest() {

    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("products.csv").getFile());

    try {

      Set<ProductDTO> productDTOSet = HelperCSV.parseProductsDTOList(
          Files.readAllBytes(file.toPath()));
      assertEquals(6, productDTOSet.size());

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
