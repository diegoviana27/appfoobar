package br.com.foobar.usecase.product;

import br.com.foobar.domain.Product;
import br.com.foobar.usecase.product.exception.ProductsEmptyException;
import br.com.foobar.usecase.product.port.ProductGateway;

import java.util.Set;

public final class FindAllProduct {

    private final ProductGateway productGateway;

    public FindAllProduct(final ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public Set<Product> findAll() {

        if (this.productGateway.findAllProduct().isEmpty()) {
            throw new ProductsEmptyException("Products is empty");
        }

        return this.productGateway.findAllProduct();
    }

}
