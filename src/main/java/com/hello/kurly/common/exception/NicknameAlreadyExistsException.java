package com.hello.kurly.common.exception;

public class NicknameAlreadyExistsException extends BusinessException {

  public NicknameAlreadyExistsException(String message) {
    super(message, ErrorCode.NICKNAME_ALREADY_EXISTS);
  }

  public NicknameAlreadyExistsException() {
    super(ErrorCode.NICKNAME_ALREADY_EXISTS);
  }
}
