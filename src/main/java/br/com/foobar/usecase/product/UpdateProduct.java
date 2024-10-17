package br.com.foobar.usecase.product;

import br.com.foobar.domain.Product;
import br.com.foobar.usecase.product.exception.ProductNotExistsException;
import br.com.foobar.usecase.product.port.ProductGateway;
import br.com.foobar.usecase.product.validator.ProductValidator;

public final class UpdateProduct {

    private final ProductGateway productGateway;

    public UpdateProduct(final ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public Product update(final Product product) {

        ProductValidator.validateCreateProduct(product);

        return this.productGateway.update(product)
                .orElseThrow(() -> new ProductNotExistsException(
                        String.format("Product with code %s was not found", product.getLm())));

    }

}
