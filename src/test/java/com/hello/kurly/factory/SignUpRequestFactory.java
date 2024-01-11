package com.hello.kurly.factory;

import com.hello.kurly.users.v1.dto.SignUpRequest;

public class SignUpRequestFactory {

  public static SignUpRequest createSignUpRequest() {
    return new SignUpRequest(
            "user1",
            "user",
            "user1@test.com",
            "01012341234",
            "20000101",
            "gender",
            "1234",
            "zipcode1",
            "address1",
            "addressDetail");
  }
}
