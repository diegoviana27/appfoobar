package br.com.foobar.usecase.product;

import br.com.foobar.usecase.product.exception.ProductNotExistsException;
import br.com.foobar.usecase.product.port.ProductGateway;

public final class DeleteProduct {

    private final ProductGateway productGateway;

    public DeleteProduct(final ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public void deleteProduct(final Long lm) {

        if (this.productGateway.findProduct(lm).isEmpty()) {
            throw new ProductNotExistsException(String.format("Product with code %s was not found", lm));
        }

        this.productGateway.deleteProduct(lm);
    }

}
