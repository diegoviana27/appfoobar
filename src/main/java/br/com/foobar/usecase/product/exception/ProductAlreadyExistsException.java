package br.com.foobar.usecase.product.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(final String message) {
        super(message);
    }

}
