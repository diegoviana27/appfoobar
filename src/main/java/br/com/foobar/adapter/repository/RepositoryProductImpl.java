package br.com.foobar.adapter.repository;

import br.com.foobar.adapter.repository.model.ProductModel;
import br.com.foobar.domain.Product;
import br.com.foobar.usecase.product.port.ProductGateway;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Transactional
@Component
public class RepositoryProductImpl implements ProductGateway {

    private final RepositoryProduct repositoryProduct;

    public RepositoryProductImpl(@Lazy final RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    @Override
    public Product create(final Product product) {

        ProductModel productModel = ProductModel.parseToModel(product);
        // persist created at, as  audit controller
        productModel.setCreatedAt(LocalDateTime.now());

        return ProductModel.parseFromModel(
                this.repositoryProduct.save(productModel));

    }

    @Override
    public Optional<Product> update(final Product product) {

        Optional<ProductModel> productModel = this.repositoryProduct.findById(product.getLm());

        if (productModel.isPresent()) {

            productModel.get().setName(product.getName());
            productModel.get().setFreeShipping(product.isFreeShipping());
            productModel.get().setDescription(product.getDescription());
            productModel.get().setPrice(product.getPrice());
            productModel.get().setCategory(product.getCategory());

            // keep created at and update last update at, as audit controller
            productModel.get().setCreatedAt(productModel.get().getCreatedAt());
            productModel.get().setLastUpdatedAt(LocalDateTime.now());

            return Optional.of(
                    ProductModel.parseFromModel(this.repositoryProduct.save(productModel.get())));

        } else {
            return Optional.empty();
        }

    }

    @Override
    public Set<Product> findAllProduct() {
        return ProductModel.parseFromListModel(this.repositoryProduct.findAll());
    }

    @Override
    public Optional<Product> findProduct(final Long lm) {

        Optional<ProductModel> productModel = this.repositoryProduct.findById(lm);

        return productModel.map(ProductModel::parseFromModel);

    }

    @Override
    public void deleteProduct(final Long lm) {
        this.repositoryProduct.deleteById(lm);
    }

}
