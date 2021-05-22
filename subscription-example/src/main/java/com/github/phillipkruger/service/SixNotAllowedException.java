package com.github.phillipkruger.service;

public class SixNotAllowedException extends Exception {

    public SixNotAllowedException() {
    }

    public SixNotAllowedException(String message) {
        super(message);
    }

    public SixNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SixNotAllowedException(Throwable cause) {
        super(cause);
    }

    public SixNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
