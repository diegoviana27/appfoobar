package br.com.foobar.usecase.product.exception;

public class ProductValidatorException extends RuntimeException {

    public ProductValidatorException(final String message) {
        super(message);
    }

}
