package br.com.foobar.product.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.foobar.application.factory.ProductFactory;
import br.com.foobar.domain.Product;
import br.com.foobar.usecase.product.CreateProduct;
import br.com.foobar.usecase.product.DeleteProduct;
import br.com.foobar.usecase.product.FindAllProduct;
import br.com.foobar.usecase.product.FindProduct;
import br.com.foobar.usecase.product.UpdateProduct;
import br.com.foobar.usecase.product.exception.ProductAlreadyExistsException;
import br.com.foobar.usecase.product.exception.ProductNotExistsException;
import br.com.foobar.usecase.product.exception.ProductPriceMustNotBeLessThanZeroException;
import br.com.foobar.usecase.product.exception.ProductValidatorException;
import br.com.foobar.usecase.product.exception.ProductsEmptyException;
import java.math.BigDecimal;
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
    scripts = {"/insert_products.sql"}
)
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
    scripts = {"/delete_products.sql"}
)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
final class ProductTest {

  @Autowired
  private ProductFactory useCase;

  private static Product buildProduct(final Long lm, final String name, final boolean freeShipping,
      final String description, final BigDecimal price, final Long category) {
    return new Product(lm, name,
        freeShipping, description,
        price, category);
  }

  @Test
  void createNewProduct() {

    Product productTest = buildProduct(10058L, "Name Example", false, "Description Example",
        BigDecimal.valueOf(50.65),
        1L);

    Product product = this.useCase.createProduct().create(productTest);

    assertNotNull(product);
    assertEquals(productTest.getLm(), product.getLm());
    assertEquals(productTest.getName(), product.getName());
    assertEquals(productTest.isFreeShipping(), product.isFreeShipping());
    assertEquals(productTest.getDescription(), product.getDescription());
    assertEquals(productTest.getPrice(), product.getPrice());
    assertEquals(productTest.getCategory(), product.getCategory());

  }

  @Test
  void updateProductWithPriceZeroLessThanZero() {

    Product product = buildProduct(1L, "Name Test 1", true, "Description Test 1",
        BigDecimal.valueOf(-3587.15), 1058L);

    UpdateProduct updateProduct = this.useCase.updateProduct();

    assertThrows(ProductPriceMustNotBeLessThanZeroException.class,
        () -> updateProduct.update(product));

  }

  @Test
  void createNewProductExists() {

    Product product = buildProduct(1L, "Name Test 1", true, "Description Test 1",
        BigDecimal.valueOf(3587.15), 1058L);

    CreateProduct createProduct = this.useCase.createProduct();

    assertThrows(ProductAlreadyExistsException.class,
        () -> createProduct.create(product));

  }

  @Test
  void createNewProductValidate() {

    CreateProduct createProduct = this.useCase.createProduct();

    // Testing without the product lm defined
    assertThrows(ProductValidatorException.class,
        () -> createProduct
            .create(null));

    // Testing without the field lm defined
    Product withoutField = buildProduct(null, "Name Test 1",
        true, "Description Test 1",
        BigDecimal.valueOf(3587.15), 1058L);

    assertThrows(ProductValidatorException.class,
        () -> createProduct
            .create(withoutField));

    // Testing without the field name defined
    Product withoutName = buildProduct(5L, null,
        true, "Description Test 1",
        BigDecimal.valueOf(3587.15), 1058L);

    assertThrows(ProductValidatorException.class,
        () -> createProduct.create(withoutName));

    // Testing without the field description defined
    Product withoutDescription = buildProduct(5L, "Name Test 1",
        true, null,
        BigDecimal.valueOf(3587.15), 1058L);

    assertThrows(ProductValidatorException.class,
        () -> createProduct.create(withoutDescription));

    // Testing without the field price defined
    Product withoutPrice = buildProduct(5L, "Name Test 1",
        true, "Description Test 1",
        null, 587L);

    assertThrows(ProductValidatorException.class,
        () -> createProduct.create(withoutPrice));

    // Testing without the field category defined
    Product withoutCategory = buildProduct(5L, "Name Test 1",
        true, "Description Test 1",
        BigDecimal.valueOf(3587.15), null);

    assertThrows(ProductValidatorException.class,
        () -> createProduct.create(withoutCategory));

  }

  @Test
  void updateProductExists() {

    Product product = this.useCase.findProduct().findProduct(1L);

    product.setName("Name Updated");
    product.setFreeShipping(true);
    product.setDescription("Description Updated");
    product.setPrice(BigDecimal.valueOf(3587.15));
    product.setCategory(1058L);

    Product productUpdated = this.useCase.updateProduct().update(product);

    assertNotNull(productUpdated);
    assertEquals(1L, productUpdated.getLm());
    assertEquals("Name Updated", productUpdated.getName());
    assertTrue(productUpdated.isFreeShipping());
    assertEquals("Description Updated", productUpdated.getDescription());
    assertEquals(BigDecimal.valueOf(3587.15), productUpdated.getPrice());
    assertEquals(1058L, productUpdated.getCategory());

  }

  @Test
  void updateProductNotExists() {

    Product product = buildProduct(55L, "Name Updated", true, "Description Updated",
        BigDecimal.valueOf(3587.15),
        1058L);

    UpdateProduct updateProduct = this.useCase.updateProduct();

    assertThrows(ProductNotExistsException.class,
        () -> updateProduct.update(product));

  }

  @Test
  void deleteProductExists() {

    DeleteProduct deleteProduct = this.useCase.deleteProduct();

    deleteProduct.deleteProduct(5L);

    assertThrows(ProductNotExistsException.class,
        () -> deleteProduct.deleteProduct(5L));

  }

  @Test
  void findAllProductsExists() {
    assertEquals(5, this.useCase.findAllProduct().findAll().size());
  }

  @Test
  @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
      scripts = {"/delete_products.sql"}
  )
  void findAllProductsNotExists() {

    FindAllProduct findAllProduct = this.useCase.findAllProduct();

    assertThrows(ProductsEmptyException.class,
        findAllProduct::findAll);

  }

  @Test
  void findProductExists() {

    Product productFoundTest = this.useCase.findProduct().findProduct(1L);

    assertNotNull(productFoundTest);
    assertEquals(1L, productFoundTest.getLm());
    assertEquals("Name Test 1", productFoundTest.getName());
    assertTrue(productFoundTest.isFreeShipping());
    assertEquals("Description Test 1", productFoundTest.getDescription());
    assertEquals(BigDecimal.valueOf(5.15), productFoundTest.getPrice());
    assertEquals(1L, productFoundTest.getCategory());

  }

  @Test
  void findProductNotExists() {

    FindProduct findProduct = this.useCase.findProduct();

    assertThrows(ProductNotExistsException.class,
        () -> findProduct.findProduct(5555L));

  }

}
