package com.hello.kurly.users.v1.controller;

import com.hello.kurly.users.domain.User;
import com.hello.kurly.users.domain.UserRepository;
import com.hello.kurly.users.v1.dto.SignInRequest;
import com.hello.kurly.users.v1.dto.SignInResponse;
import com.hello.kurly.users.v1.dto.UserResponse;
import com.hello.kurly.users.v1.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.hello.kurly.factory.UserFactory.createAdmin;
import static com.hello.kurly.factory.UserFactory.createUser;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  void init() {
    userRepository.save(createUser(passwordEncoder));
    userRepository.save(createAdmin(passwordEncoder));
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("인증된 회원은 자신의 정보를 가져올 수 있다")
  void getMe() throws URISyntaxException {
    //given
    String accessToken = getToken("user1", "1234");

    //when
    ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(
            uri("/v1/users/me"),
            HttpMethod.GET,
            getAuthHeaderEntity(accessToken),
            UserResponse.class);

    //then
    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    assertThat(responseEntity.getBody().getNickname()).isEqualTo("user1");
  }

  @Test
  @DisplayName("ADMIN 권한이 있으면 회원 정보를 가져올 수 있다")
  void getUser() throws URISyntaxException {
    //given
    List<User> users = userRepository.findAll();
    User user = users.get(0);
    Long userId = user.getId();

    String accessToken = getToken("admin", "admin");

    //when
    ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(
            uri("/v1/users/" + userId),
            HttpMethod.GET,
            getAuthHeaderEntity(accessToken),
            UserResponse.class);

    //then
    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    assertThat(responseEntity.getBody().getNickname()).isEqualTo(user.getNickname());
  }

  private String getToken(String nickname, String password) throws URISyntaxException {
    SignInRequest request = new SignInRequest(nickname, password);
    HttpEntity<SignInRequest> body = new HttpEntity<>(request);

    ResponseEntity<SignInResponse> responseEntity = restTemplate.exchange(
            uri("/v1/users/sign-in"),
            HttpMethod.POST,
            body,
            SignInResponse.class);

    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    return responseEntity.getBody().getAccessToken();
  }

  private URI uri(String path) throws URISyntaxException {
    return new URI(format("http://localhost:%d%s", port, path));
  }

  private HttpEntity getAuthHeaderEntity(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    HttpEntity entity = new HttpEntity("", headers);
    return entity;
  }
}
