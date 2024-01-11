package com.hello.kurly.users.domain;

import com.hello.kurly.config.ApplicationConfig;
import com.hello.kurly.config.AuditConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.hello.kurly.factory.UserFactory.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Import({AuditConfig.class, ApplicationConfig.class})
@DataJpaTest(showSql = false)
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("회원을 저장하고 회원아이디 또는 이메일로 조회한다")
  void saveUser() {
    //given
    LocalDateTime now = LocalDateTime.now();
    User user = userRepository.save(createUser(passwordEncoder));

    //when
    Optional<User> findUserByNickname = userRepository.findByNickname(user.getNickname());
    Optional<User> findUserByEmail = userRepository.findByEmail(user.getEmail());
    boolean nicknameExists = userRepository.existsByNickname(user.getNickname());
    boolean emailExists = userRepository.existsByEmail(user.getEmail());

    //then
    assertThat(findUserByNickname.get().getNickname()).isEqualTo(user.getNickname());
    assertThat(findUserByNickname.get().getCreatedAt()).isAfter(now);
    assertThat(findUserByNickname.get().getUpdatedAt()).isAfter(now);
    assertThat(findUserByEmail.get().getEmail()).isEqualTo(user.getEmail());
    assertThat(nicknameExists).isTrue();
    assertThat(emailExists).isTrue();
  }

  @Test
  @DisplayName("회원아이디 또는 이메일이 중복인 경우 예외가 발생한다")
  void throwExceptionByNicknameOrEmailDuplication() {
    //given
    User user = createUser(passwordEncoder);
    User sameNicknameAsUser = createOtherUserWithNickname(user.getNickname());
    User sameEmailAsUser = createOtherUserWithEmail(user.getEmail());
    userRepository.save(user);

    //when, then
    assertThatThrownBy(() -> {
      userRepository.save(sameNicknameAsUser);
    }).isInstanceOf(DataIntegrityViolationException.class);
    assertThatThrownBy(() -> {
      userRepository.save(sameEmailAsUser);
    }).isInstanceOf(DataIntegrityViolationException.class);
  }
}