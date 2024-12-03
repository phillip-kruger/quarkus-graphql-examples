package com.github.phillipkruger;

public class ExchangeRateNotAvailableException extends Exception {

    public ExchangeRateNotAvailableException() {
    }

    public ExchangeRateNotAvailableException(String message) {
        super(message);
    }

    public ExchangeRateNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExchangeRateNotAvailableException(Throwable cause) {
        super(cause);
    }

    public ExchangeRateNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
