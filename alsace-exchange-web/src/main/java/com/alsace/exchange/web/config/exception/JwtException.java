package com.alsace.exchange.web.config.exception;

/**
 * JWT相关的异常
 */
public class JwtException extends RuntimeException{
  private static final long serialVersionUID = -6934076071951954522L;

  public JwtException() {
    super();
  }

  public JwtException(String message) {
    super(message);
  }

  public JwtException(String message, Throwable cause) {
    super(message, cause);
  }

  public JwtException(Throwable cause) {
    super(cause);
  }

  protected JwtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
