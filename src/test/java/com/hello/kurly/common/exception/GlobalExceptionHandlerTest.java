package com.hello.kurly.common.exception;

import com.hello.kurly.common.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JwtService jwtService;

  @BeforeEach
  void setUp(WebApplicationContext context) {
    this.mockMvc = webAppContextSetup(context)
            .alwaysDo(print())
            .build();
  }

  @Test
  @DisplayName("요청 값 타입이 다르면 오류 응답을 반환한다")
  void handleMethodArgumentTypeMismatchException() throws Exception {
    mockMvc.perform(get("/v1/users/{id}", "StringValue")
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("status").value(ErrorCode.BAD_REQUEST.getStatus()))
           .andExpect(jsonPath("code").value(ErrorCode.BAD_REQUEST.getCode()))
           .andExpect(jsonPath("message").value(ErrorCode.BAD_REQUEST.getMessage()));
  }

  @Test
  @DisplayName("지원하지 않는 http request method 이면 오류 응답을 반환한다")
  void handleHttpRequestMethodNotSupportedException() throws Exception {
    mockMvc.perform(post("/v1/users/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
           .andExpect(status().isMethodNotAllowed())
           .andExpect(jsonPath("status").value(ErrorCode.METHOD_NOT_ALLOWED.getStatus()))
           .andExpect(jsonPath("code").value(ErrorCode.METHOD_NOT_ALLOWED.getCode()))
           .andExpect(jsonPath("message").value(ErrorCode.METHOD_NOT_ALLOWED.getMessage()));
  }

  @Test
  @DisplayName("인증되지 않은 요청인 경우 오류 응답을 반환한다")
  void handleAuthenticationException() throws Exception {
    mockMvc.perform(get("/v1/users/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isUnauthorized())
           .andExpect(jsonPath("status").value(ErrorCode.UNAUTHORIZED.getStatus()))
           .andExpect(jsonPath("code").value(ErrorCode.UNAUTHORIZED.getCode()))
           .andExpect(jsonPath("message").value(ErrorCode.UNAUTHORIZED.getMessage()));
  }

  @Test
  @DisplayName("ADMIN 권한이 없으면 오류 응답을 반환한다")
  @WithMockUser(authorities = "NORMAL")
  void handle_() throws Exception {
    mockMvc.perform(get("/v1/users/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isUnauthorized())
           .andExpect(jsonPath("status").value(ErrorCode.UNAUTHORIZED.getStatus()))
           .andExpect(jsonPath("code").value(ErrorCode.UNAUTHORIZED.getCode()))
           .andExpect(jsonPath("message").value(ErrorCode.UNAUTHORIZED.getMessage()));
  }

  @Test
  @DisplayName("회원 정보가 없으면 오류 응답을 반환한다")
  @WithMockUser(authorities = "ADMIN")
  void handleBusinessException() throws Exception {
    mockMvc.perform(get("/v1/users/{id}", 1)
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("status").value(ErrorCode.USER_NOT_FOUND.getStatus()))
           .andExpect(jsonPath("code").value(ErrorCode.USER_NOT_FOUND.getCode()))
           .andExpect(jsonPath("message").value(ErrorCode.USER_NOT_FOUND.getMessage()));
  }

  @Test
  void handleException() throws Exception {
    //TODO 정의되지 않은 예외 확인
  }
}