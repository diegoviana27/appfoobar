package br.com.foobar.usecase.product.exception;

public class ProductNotExistsException extends RuntimeException {

    public ProductNotExistsException(final String message) {
        super(message);
    }

}
