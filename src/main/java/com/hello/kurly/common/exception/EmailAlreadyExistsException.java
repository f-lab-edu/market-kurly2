package com.hello.kurly.common.exception;

public class EmailAlreadyExistsException extends BusinessException {

  public EmailAlreadyExistsException(String message) {
    super(message, ErrorCode.EMAIL_ALREADY_EXISTS);
  }

  public EmailAlreadyExistsException() {
    super(ErrorCode.EMAIL_ALREADY_EXISTS);
  }
}
