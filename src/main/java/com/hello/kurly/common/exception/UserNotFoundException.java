package com.hello.kurly.common.exception;

public class UserNotFoundException extends BusinessException {

  public UserNotFoundException(String message) {
    super(message, ErrorCode.USER_NOT_FOUND);
  }

  public UserNotFoundException() {
    super(ErrorCode.USER_NOT_FOUND);
  }
}
