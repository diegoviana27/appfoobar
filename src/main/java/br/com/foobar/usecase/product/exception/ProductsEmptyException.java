package br.com.foobar.usecase.product.exception;

public class ProductsEmptyException extends RuntimeException {

    public ProductsEmptyException(final String message) {
        super(message);
    }

}
