package br.com.foobar.usecase.product;

import br.com.foobar.domain.Product;
import br.com.foobar.usecase.product.exception.ProductNotExistsException;
import br.com.foobar.usecase.product.port.ProductGateway;

public final class FindProduct {

    private final ProductGateway productGateway;

    public FindProduct(final ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public Product findProduct(final Long lm) {
        return this.productGateway.findProduct(lm).orElseThrow(
                () -> new ProductNotExistsException(
                        String.format("Product with code %s was not found", lm)));
    }

}
