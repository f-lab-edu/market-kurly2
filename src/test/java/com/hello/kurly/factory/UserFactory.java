package com.hello.kurly.factory;

import com.hello.kurly.users.domain.GradeType;
import com.hello.kurly.users.domain.RoleType;
import com.hello.kurly.users.domain.StatusType;
import com.hello.kurly.users.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserFactory {

  public static User createUser(PasswordEncoder passwordEncoder) {
    return new User(
            "user1",
            StatusType.NORMAL,
            RoleType.NORMAL,
            GradeType.GENERAL,
            "user",
            "user1@test.com",
            "01012341234",
            LocalDate.now(),
            "gender",
            passwordEncoder.encode("1234"),
            null,
            new ArrayList<>());
  }

  public static User createOtherUserWithNickname(String nickname) {
    return new User(
            nickname,
            StatusType.NORMAL,
            RoleType.NORMAL,
            GradeType.GENERAL,
            "user",
            "user2@test.com",
            "01012341234",
            LocalDate.parse("2000-01-01"),
            "gender",
            "1234",
            null,
            new ArrayList<>());
  }

  public static User createOtherUserWithEmail(String email) {
    return new User(
            "user2",
            StatusType.NORMAL,
            RoleType.NORMAL,
            GradeType.GENERAL,
            "user",
            email,
            "01012341234",
            LocalDate.parse("2000-01-01"),
            "gender",
            "1234",
            null,
            new ArrayList<>());
  }

  public static User createAdmin(PasswordEncoder passwordEncoder) {
    return new User(
            "admin",
            StatusType.NORMAL,
            RoleType.ADMIN,
            null,
            "admin",
            "admin@test.com",
            "mobileNumber",
            null,
            null,
            passwordEncoder.encode("admin"),
            null,
            null);
  }
}
