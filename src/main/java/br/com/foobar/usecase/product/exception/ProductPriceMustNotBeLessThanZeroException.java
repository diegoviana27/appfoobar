package br.com.foobar.usecase.product.exception;

public class ProductPriceMustNotBeLessThanZeroException extends RuntimeException {

    public ProductPriceMustNotBeLessThanZeroException(final String message) {
        super(message);
    }

}
