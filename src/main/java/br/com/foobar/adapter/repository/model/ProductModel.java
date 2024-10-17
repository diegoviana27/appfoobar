package br.com.foobar.adapter.repository.model;

import br.com.foobar.domain.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "products")
public class ProductModel implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;

    @Id
    private Long lm;

    @Column
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 250, message = "Name must be between 2 and 250 characters")
    private String name;

    @Column
    private boolean freeShipping;

    @Column
    @Size(min = 2, max = 250, message = "Description must be between 2 and 250 characters")
    @NotNull(message = "Description cannot be null")
    private String description;

    @Column
    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

    @Column
    @NotNull(message = "Category cannot be null")
    private Long category;

    @Column
    @NotNull(message = "CreateAt cannot be null")
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime lastUpdatedAt;

    public ProductModel(final Long lm, final String name, final boolean freeShipping,
                        final String description,
                        final BigDecimal price, final Long category) {

        this.lm = lm;
        this.name = name;
        this.freeShipping = freeShipping;
        this.description = description;
        this.price = price;
        this.category = category;

    }

    public static ProductModel parseToModel(final Product product) {
        return new ProductModel(product.getLm(), product.getName(), product.isFreeShipping(),
                product.getDescription(), product.getPrice(), product.getCategory());
    }

    public static Product parseFromModel(final ProductModel productModel) {
        return new Product(productModel.getLm(), productModel.getName(), productModel.isFreeShipping(),
                productModel.getDescription(), productModel.getPrice(), productModel.getCategory());
    }

    public static Set<Product> parseFromListModel(final List<ProductModel> productModels) {
        return productModels.stream().map(productModel ->
                new Product(productModel.getLm(), productModel.getName(), productModel.isFreeShipping(),
                        productModel.getDescription(), productModel.getPrice(), productModel.getCategory())
        ).collect(Collectors.toSet());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        ProductModel that = (ProductModel) o;
        return lm != null && Objects.equals(lm, that.lm);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
