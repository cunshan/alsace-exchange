package com.alsace.exchange.web.config.exception;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.exception.AlsaceException;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {


  /**
   * 业务异常处理
   */
  @ExceptionHandler(AlsaceException.class)
  @ResponseBody
  public AlsaceResult<String> bizException(AlsaceException ex) {
    log.error(Throwables.getStackTraceAsString(ex));
    return new AlsaceResult<String>().setSuccess(false).setMsg(ex.getMessage()).setErrorCode(ex.getErrorCode());
  }

  /**
   * 业务异常处理
   */
  @ExceptionHandler(JwtException.class)
  @ResponseBody
  public AlsaceResult<String> jwtException(JwtException ex) {
    log.error(Throwables.getStackTraceAsString(ex));
    return new AlsaceResult<String>().setSuccess(false).setMsg(ex.getMessage()).setErrorCode("");
  }

  /**
   * 业务异常处理
   */
  @ExceptionHandler(IllegalStateException.class)
  @ResponseBody
  public AlsaceResult<String> bizException(IllegalStateException ex) {
    log.error(Throwables.getStackTraceAsString(ex));
    return new AlsaceResult<String>().setSuccess(false).setMsg(ex.getMessage());
  }


  /**
   * 业务异常处理
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public AlsaceResult<String> bizException(MethodArgumentNotValidException ex) {
    log.error(Throwables.getStackTraceAsString(ex));
    return new AlsaceResult<String>().setSuccess(false).setMsg(ex.getBindingResult().getFieldError().getDefaultMessage());
  }
  /**
   * 参数异常处理
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  public AlsaceResult<String> bizException(IllegalArgumentException ex) {
    log.error(Throwables.getStackTraceAsString(ex));
    return new AlsaceResult<String>().setSuccess(false).setMsg(ex.getMessage());
  }


  /**
   * 未处理的异常处理
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public AlsaceResult<String> exception(Exception ex) {
    String errMsg = Throwables.getStackTraceAsString(ex);
    log.error(errMsg);
    return new AlsaceResult<String>().setSuccess(false).setMsg(errMsg);
  }


}
