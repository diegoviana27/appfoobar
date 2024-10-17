package br.com.foobar.application.config.exception;

import br.com.foobar.usecase.processcontrol.exception.ProcessControlNotExistsException;
import br.com.foobar.usecase.product.exception.ProductNotExistsException;
import br.com.foobar.usecase.product.exception.ProductPriceMustNotBeLessThanZeroException;
import br.com.foobar.usecase.product.exception.ProductValidatorException;
import br.com.foobar.usecase.product.exception.ProductsEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(ProductValidatorException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ValidationError handleProductValidatorException(final ProductValidatorException e) {
        LOGGER.error(e.getMessage());
        return new ValidationError(e.getMessage());
    }

    @ExceptionHandler(ProductPriceMustNotBeLessThanZeroException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ValidationError handleProductPriceMustNotBeLessThanZeroException(
            final ProductPriceMustNotBeLessThanZeroException e) {
        LOGGER.error(e.getMessage());
        return new ValidationError(e.getMessage());
    }

    @ExceptionHandler(ProcessControlNotExistsException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ValidationError handleProcessControlNotExistsException(final ProcessControlNotExistsException e) {
        LOGGER.warn(e.getMessage());
        return new ValidationError(e.getMessage());
    }

    @ExceptionHandler(ProductNotExistsException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ValidationError handleProductNotExistsException(final ProductNotExistsException e)
            throws ProductsEmptyException {
        LOGGER.warn(e.getMessage());
        return new ValidationError(e.getMessage());
    }

    @ExceptionHandler(ProductsEmptyException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ValidationError handleProductsEmptyException(final ProductsEmptyException e) {
        LOGGER.warn(e.getMessage());
        return new ValidationError(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ValidationError handleConstraintViolationException(final ConstraintViolationException e) {
        LOGGER.error(e.getMessage());
        return new ValidationError(e.getMessage());
    }

}

