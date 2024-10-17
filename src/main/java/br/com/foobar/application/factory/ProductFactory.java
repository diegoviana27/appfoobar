package br.com.foobar.application.factory;

import br.com.foobar.adapter.controller.ProductController;
import br.com.foobar.adapter.repository.RepositoryProductImpl;
import br.com.foobar.usecase.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

    @Autowired
    RepositoryProductImpl repositoryProduct;

    @Bean
    public CreateProduct createProduct() {
        return new CreateProduct(this.repositoryProduct);
    }

    @Bean
    public UpdateProduct updateProduct() {
        return new UpdateProduct(this.repositoryProduct);
    }

    @Bean
    public FindAllProduct findAllProduct() {
        return new FindAllProduct(this.repositoryProduct);
    }

    @Bean
    public FindProduct findProduct() {
        return new FindProduct(this.repositoryProduct);
    }

    @Bean
    public DeleteProduct deleteProduct() {
        return new DeleteProduct(this.repositoryProduct);
    }

    @Bean
    public ProductController userControllerProduct() {
        return new ProductController(createProduct(), updateProduct(), findAllProduct(),
                findProduct(), deleteProduct());
    }
}
