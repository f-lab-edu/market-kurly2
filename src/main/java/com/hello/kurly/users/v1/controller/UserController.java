package com.hello.kurly.users.v1.controller;

import com.hello.kurly.users.v1.dto.*;
import com.hello.kurly.users.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/sign-up")
  public UserResponse signUp(@RequestBody SignUpRequest request) {
    return userService.signUp(request);
  }

  @PostMapping("/sign-in")
  public SignInResponse signIn(@RequestBody SignInRequest request) {
    return userService.signIn(request);
  }

  @GetMapping("/me")
  public UserResponse getMe(Authentication authentication) {
    return userService.getMe(authentication);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public UserResponse getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @PutMapping("/{id}/profile")
  public Long updateProfile(@PathVariable Long id) {
    return null;
  }

  @GetMapping("/existence")
  public boolean isExistTarget(@RequestParam("target") ExistTarget target,
                               @RequestParam("value") String value) {
    return false;
  }
}
