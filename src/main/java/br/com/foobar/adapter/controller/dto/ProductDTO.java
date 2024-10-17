package br.com.foobar.adapter.controller.dto;

import br.com.foobar.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProductDTO implements Serializable {

    private Long lm;

    private String name;

    private boolean freeShipping;

    private String description;

    @Schema(type = "number", example = "100.58")
    private BigDecimal price;

    private Long category;

    public ProductDTO(final Long lm, final String name, final boolean freeShipping,
                      final String description,
                      final BigDecimal price,
                      final Long category) {

        this.lm = lm;
        this.name = name;
        this.freeShipping = freeShipping;
        this.description = description;
        this.price = price;
        this.category = category;

    }

    public static ProductDTO parseToDto(final Product product) {
        return new ProductDTO(product.getLm(), product.getName(), product.isFreeShipping(),
                product.getDescription(), product.getPrice(), product.getCategory());
    }

    public static Product parseFromDto(final ProductDTO productDTO) {
        return new Product(productDTO.getLm(), productDTO.getName(), productDTO.isFreeShipping(),
                productDTO.getDescription(), productDTO.getPrice(), productDTO.getCategory());
    }

    public static Set<ProductDTO> parseToListDto(final Set<Product> products) {
        return products.stream().map(ProductDTO::parseToDto).collect(Collectors.toSet());
    }

}
