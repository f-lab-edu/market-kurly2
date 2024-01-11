package com.hello.kurly.users.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.kurly.common.exception.InvalidPasswordException;
import com.hello.kurly.common.exception.NicknameAlreadyExistsException;
import com.hello.kurly.common.exception.UserNotFoundException;
import com.hello.kurly.common.jwt.JwtService;
import com.hello.kurly.config.RestDocsConfig;
import com.hello.kurly.factory.SignUpRequestFactory;
import com.hello.kurly.users.v1.dto.*;
import com.hello.kurly.users.v1.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  protected RestDocumentationResultHandler restDocs;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserService userService;

  @MockBean
  private JwtService jwtService;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider provider) {
    this.mockMvc = webAppContextSetup(context)
            .apply(documentationConfiguration(provider))
            .alwaysDo(restDocs)
            .alwaysDo(print())
            .build();
  }

  @Test
  @DisplayName("회원가입을 성공한다")
  void signUp() throws Exception {
    //given
    SignUpRequest request = SignUpRequestFactory.createSignUpRequest();
    ArrayList<AddressResponse> addresses = new ArrayList<>();
    addresses.add(new AddressResponse(
            "zipCode1",
            "address1",
            "addressDetail"));

    //when
    when(userService.signUp(any()))
            .thenReturn(new UserResponse(
                    "user1",
                    "GENERAL",
                    "user",
                    addresses));

    //then
    mockMvc.perform(post("/v1/users/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andDo(restDocs.document(
                          requestFields(
                                  fieldWithPath("nickname").description("회원아이디"),
                                  fieldWithPath("name").description("회원명"),
                                  fieldWithPath("email").description("이메일"),
                                  fieldWithPath("mobileNumber").description("휴대폰번호"),
                                  fieldWithPath("birthday").description("생일"),
                                  fieldWithPath("gender").description("성별"),
                                  fieldWithPath("password").description("비밀번호"),
                                  fieldWithPath("zipCode").description("우편번호"),
                                  fieldWithPath("address").description("주소"),
                                  fieldWithPath("addressDetail").description("상세주소")
                          ),
                          responseFields(
                                  fieldWithPath("nickname").description("회원아이디"),
                                  fieldWithPath("grade").description("등급"),
                                  fieldWithPath("name").description("회원명"),
                                  fieldWithPath("addresses").description("배송지목록")
                          ).andWithPrefix(
                                  "addresses.[].",
                                  fieldWithPath("zipCode").description("우편번호"),
                                  fieldWithPath("address").description("기본주소"),
                                  fieldWithPath("addressDetail").description("상세주소"))
                  )
           );
  }

  @Test
  @DisplayName("회원아이디가 이미 존재하는 경우 회원가입을 실패한다")
  void signUpFailByNicknameAlreadyExists() throws Exception {
    //given
    SignUpRequest request = SignUpRequestFactory.createSignUpRequest();

    //when
    when(userService.signUp(any()))
            .thenThrow(new NicknameAlreadyExistsException());

    //then
    mockMvc.perform(post("/v1/users/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("로그인을 성공한다")
  void signIn() throws Exception {
    //given
    SignInRequest request = new SignInRequest("user1", "1234");

    //when
    when(userService.signIn(any()))
            .thenReturn(new SignInResponse("accessToken", null));

    //then
    mockMvc.perform(post("/v1/users/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
            .andDo(restDocs.document(
                    requestFields(
                            fieldWithPath("nickname").description("회원아이디"),
                            fieldWithPath("password").description("비밀번호")
                    ),
                    responseFields(
                            fieldWithPath("accessToken").description("AccessToken"),
                            fieldWithPath("refreshToken").description("RefreshToken")
                    )
            ));
  }

  @Test
  @DisplayName("회원아이디가 존재하지 않는 경우 로그인을 실패한다")
  void signInFailByUserNotFound() throws Exception {
    //given
    SignInRequest request = new SignInRequest("nickname1", "password1");

    //when
    when(userService.signIn(any()))
            .thenThrow(new UserNotFoundException());

    //then
    mockMvc.perform(post("/v1/users/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("비밀번호가 맞지 않는 경우 로그인을 실패한다")
  void signInFailByInvalidPassword() throws Exception {
    //given
    SignInRequest request = new SignInRequest("nickname1", "password1");

    //when
    when(userService.signIn(any()))
            .thenThrow(new InvalidPasswordException());

    //then
    mockMvc.perform(post("/v1/users/sign-in")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isUnauthorized());
  }

  @Test
  void getMe() {
    // TODO 내정보 조회
  }

  @Test
  void getUser() {
    //TODO 회원정보 조회
  }

  @Test
  void updateProfile() {
    // TODO 회원정보 수정
  }

  @Test
  void isExistTarget() {
    // TODO 존재 여부 확인
  }
}