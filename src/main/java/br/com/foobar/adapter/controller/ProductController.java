package br.com.foobar.adapter.controller;

import br.com.foobar.adapter.controller.dto.ProductDTO;
import br.com.foobar.domain.Product;
import br.com.foobar.usecase.product.*;

import java.util.Set;

public class ProductController {

    private final CreateProduct createProduct;

    private final UpdateProduct updateProduct;

    private final FindAllProduct findAllProduct;

    private final FindProduct findProduct;

    private final DeleteProduct deleteProduct;

    public ProductController(final CreateProduct createProduct, final UpdateProduct updateProduct,
                             final FindAllProduct findAllProduct, final FindProduct findProduct,
                             final DeleteProduct deleteProduct) {

        this.createProduct = createProduct;
        this.updateProduct = updateProduct;
        this.findAllProduct = findAllProduct;
        this.findProduct = findProduct;
        this.deleteProduct = deleteProduct;

    }

    public void create(final Product product) {
        this.createProduct.create(product);
    }

    public ProductDTO update(final Product product) {
        return ProductDTO.parseToDto(this.updateProduct.update(product));
    }

    public Set<ProductDTO> findAllProduct() {
        return ProductDTO.parseToListDto(this.findAllProduct.findAll());
    }

    public void deleteProduct(final Long lm) {
        this.deleteProduct.deleteProduct(lm);
    }

    public ProductDTO findProduct(final Long lm) {
        return ProductDTO.parseToDto(this.findProduct.findProduct(lm));
    }
}
