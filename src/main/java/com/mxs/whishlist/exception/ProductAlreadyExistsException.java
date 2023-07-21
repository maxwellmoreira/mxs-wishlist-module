package com.mxs.whishlist.exception;

/**
 * Class responsible for representing an exception when a record already exists in the persistence mechanism.
 */
public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
