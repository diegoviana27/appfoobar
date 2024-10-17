package br.com.foobar.usecase.product;

import br.com.foobar.domain.Product;
import br.com.foobar.usecase.product.exception.ProductAlreadyExistsException;
import br.com.foobar.usecase.product.port.ProductGateway;
import br.com.foobar.usecase.product.validator.ProductValidator;

public final class CreateProduct {

    private final ProductGateway productGateway;

    public CreateProduct(final ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public Product create(final Product product) {

        ProductValidator.validateCreateProduct(product);

        if (this.productGateway.findProduct(product.getLm()).isPresent()) {
            throw new ProductAlreadyExistsException(
                    String.format("Product already exists with the code %s", product.getLm()));
        }

        return this.productGateway.create(product);
    }

}
