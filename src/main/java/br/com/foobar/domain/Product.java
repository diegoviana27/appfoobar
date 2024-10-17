package br.com.foobar.domain;

import java.math.BigDecimal;

public class Product {

    private final Long lm;

    private String name;

    private boolean freeShipping;

    private String description;

    private BigDecimal price;

    private Long category;

    public Product(final Long lm, final String name, final boolean freeShipping,
                   final String description, final BigDecimal price, final Long category) {

        this.lm = lm;
        this.name = name;
        this.freeShipping = freeShipping;
        this.description = description;
        this.price = price;
        this.category = category;

    }

    public Long getLm() {
        return lm;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(final boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(final Long category) {
        this.category = category;
    }

}
