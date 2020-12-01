package com.alsace.exchange.common.exception;

public class AlsaceException extends RuntimeException{
    private static final long serialVersionUID = -9149947871782927397L;

    public AlsaceException() {
        super();
    }

    public AlsaceException(String message) {
        super(message);
    }

    public AlsaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlsaceException(Throwable cause) {
        super(cause);
    }

    protected AlsaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
