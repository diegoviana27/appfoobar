package br.com.foobar.product.unit;

import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.foobar.domain.Product;
import br.com.foobar.usecase.product.CreateProduct;
import br.com.foobar.usecase.product.exception.ProductPriceMustNotBeLessThanZeroException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
final class ProductTest {

  @Autowired
  private CreateProduct createProduct;

  @Test
  void createNewProductValidatePrice() {

    Product productTest = new Product(10058L, "Name Example", false, "Description Example",
        BigDecimal.valueOf(-50.65),
        1L);

    assertThrows(ProductPriceMustNotBeLessThanZeroException.class,
        () -> this.createProduct.create(productTest));

  }


}
