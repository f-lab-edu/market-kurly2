package com.hello.kurly.users.v1.dto;

import com.hello.kurly.users.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class UserResponse {

  private String nickname;
  private String grade;
  private String name;
  private List<AddressResponse> addresses = new ArrayList<>();

  public UserResponse(String nickname, String grade, String name, List<AddressResponse> addresses) {
    this.nickname = nickname;
    this.grade = grade;
    this.name = name;
    this.addresses = addresses;
  }

  private UserResponse(User user) {
    this.nickname = user.getNickname();
    this.grade = String.valueOf(user.getGrade());
    this.name = user.getName();
    this.addresses = new ArrayList<>();
  }

  public static UserResponse from(User user) {
    return new UserResponse(user);
  }
}