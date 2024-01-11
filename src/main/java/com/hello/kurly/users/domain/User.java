package com.hello.kurly.users.domain;

import com.hello.kurly.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; //pk

  @Column(nullable = false, length = 20, unique = true)
  private String nickname; //회원아이디

  @Enumerated(EnumType.STRING)
  private StatusType status; //회원상태(일반, 비활동, 탈퇴)

  @Enumerated(EnumType.STRING)
  private RoleType role; //권한(사용자, 관리자)

  @Enumerated(EnumType.STRING)
  private GradeType grade; //등급(일반, 프렌즈, ...)

  @Column(nullable = false, length = 20)
  private String name; //회원명

  @Column(nullable = false, length = 30, unique = true)
  private String email; //이메일

  @Column(nullable = false)
  private String mobileNumber; //휴대폰번호

  private LocalDate birthday; //생일

  private String gender; //성별(NONE, MALE, FEMALE)

  private String password; //비밀번호

  private BigInteger defaultDeliveryAddressId; //기본배송지

  @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
  private List<UserAddress> userAddresses = new ArrayList<>();

  public User(String nickname,
              StatusType status,
              RoleType role,
              GradeType grade,
              String name,
              String email,
              String mobileNumber,
              LocalDate birthday,
              String gender,
              String password,
              BigInteger defaultDeliveryAddressId,
              List<UserAddress> userAddresses) {
    this.nickname = nickname;
    this.status = status;
    this.role = role;
    this.grade = grade;
    this.name = name;
    this.email = email;
    this.mobileNumber = mobileNumber;
    this.birthday = birthday;
    this.gender = gender;
    this.password = password;
    this.defaultDeliveryAddressId = defaultDeliveryAddressId;
    this.userAddresses = userAddresses;
  }

  public void addAccountStatus() {
    status = StatusType.NORMAL;
  }

  public void addAuthority() {
    role = RoleType.NORMAL;
  }

  public void addBasicGrade() {
    grade = GradeType.GENERAL;
  }

  public void encodePassword(PasswordEncoder passwordEncoder) {
    password = passwordEncoder.encode(password);
  }
}
