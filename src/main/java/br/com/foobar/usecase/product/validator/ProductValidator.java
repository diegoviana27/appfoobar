package br.com.foobar.usecase.product.validator;

import br.com.foobar.domain.Product;
import br.com.foobar.usecase.product.exception.ProductPriceMustNotBeLessThanZeroException;
import br.com.foobar.usecase.product.exception.ProductValidatorException;

import java.math.BigDecimal;

public class ProductValidator {

    private ProductValidator() {
    }

    public static void validateCreateProduct(final Product product) {
        if (product == null) {
            throw new ProductValidatorException("Product should not be null");
        }
        if (product.getLm() == null) {
            throw new ProductValidatorException("Lm should not be null");
        }
        if (product.getName() == null) {
            throw new ProductValidatorException("Name should not be null");
        }
        if (product.getDescription() == null) {
            throw new ProductValidatorException("Description should not be null");
        }
        if (product.getPrice() == null) {
            throw new ProductValidatorException("Price should not be null");
        }
        if (product.getPrice() != null && product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductPriceMustNotBeLessThanZeroException(
                    "Product price must not be less than zero");
        }
        if (product.getCategory() == null) {
            throw new ProductValidatorException("Category should not be null");
        }
    }

}
