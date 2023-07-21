package com.mxs.whishlist.exception;

/**
 * Class responsible for representing an exception when a record is not found.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
