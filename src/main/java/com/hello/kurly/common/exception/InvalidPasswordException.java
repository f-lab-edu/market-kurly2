package com.hello.kurly.common.exception;

public class InvalidPasswordException extends BusinessException {

  public InvalidPasswordException(String message) {
    super(message, ErrorCode.INVALID_PASSWORD);
  }

  public InvalidPasswordException() {
    super(ErrorCode.INVALID_PASSWORD);
  }
}
