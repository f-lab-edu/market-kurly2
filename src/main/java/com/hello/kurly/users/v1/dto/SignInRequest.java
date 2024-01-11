package com.hello.kurly.users.v1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class SignInRequest {

  private String nickname;
  private String password;

  public SignInRequest(String nickname, String password) {
    this.nickname = nickname;
    this.password = password;
  }
}
