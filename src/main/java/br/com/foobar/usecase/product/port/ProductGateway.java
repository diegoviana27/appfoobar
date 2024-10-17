package br.com.foobar.usecase.product.port;

import br.com.foobar.domain.Product;

import java.util.Optional;
import java.util.Set;

public interface ProductGateway {

    Product create(final Product product);

    Optional<Product> update(final Product product);

    Set<Product> findAllProduct();

    Optional<Product> findProduct(final Long lm);

    void deleteProduct(final Long lm);

}
