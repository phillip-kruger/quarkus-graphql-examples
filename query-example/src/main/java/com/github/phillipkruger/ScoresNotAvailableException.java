package com.github.phillipkruger;

public class ScoresNotAvailableException extends Exception {

    public ScoresNotAvailableException() {
    }

    public ScoresNotAvailableException(String message) {
        super(message);
    }

    public ScoresNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScoresNotAvailableException(Throwable cause) {
        super(cause);
    }

    public ScoresNotAvailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
