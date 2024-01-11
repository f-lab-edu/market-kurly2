package com.hello.kurly.users.v1.service;

import com.hello.kurly.common.exception.EmailAlreadyExistsException;
import com.hello.kurly.common.exception.InvalidPasswordException;
import com.hello.kurly.common.exception.NicknameAlreadyExistsException;
import com.hello.kurly.common.exception.UserNotFoundException;
import com.hello.kurly.common.jwt.JwtService;
import com.hello.kurly.users.domain.User;
import com.hello.kurly.users.domain.UserRepository;
import com.hello.kurly.users.v1.dto.SignInRequest;
import com.hello.kurly.users.v1.dto.SignInResponse;
import com.hello.kurly.users.v1.dto.SignUpRequest;
import com.hello.kurly.users.v1.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Transactional
  public UserResponse signUp(SignUpRequest request) {
    validateSignUp(request);
    User user = request.toEntity();
    user.addAccountStatus();
    user.addAuthority();
    user.addBasicGrade();
    user.encodePassword(passwordEncoder);
    return UserResponse.from(userRepository.save(user));
  }

  public SignInResponse signIn(SignInRequest request) {
    User findUser = userRepository.findByNickname(request.getNickname()).orElseThrow(UserNotFoundException::new);
    validatePassword(request, findUser);
    String token = jwtService.generateToken(findUser);
    return new SignInResponse(token, null);
  }

  public UserResponse getMe(Authentication authentication) {
    User findUser = userRepository.findByNickname(authentication.getName()).orElseThrow(UserNotFoundException::new);
    return UserResponse.from(findUser);
  }

  public UserResponse getUser(Long id) {
    User findUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    return UserResponse.from(findUser);
  }

  public User getUser(String nickName) {
    return userRepository.findByNickname(nickName).orElseThrow(UserNotFoundException::new);
  }

  private void validateSignUp(SignUpRequest request) {
    if (userRepository.existsByNickname(request.getNickname())) {
      throw new NicknameAlreadyExistsException();
    }
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new EmailAlreadyExistsException();
    }
  }

  private void validatePassword(SignInRequest request, User user) {
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new InvalidPasswordException();
    }
  }
}
