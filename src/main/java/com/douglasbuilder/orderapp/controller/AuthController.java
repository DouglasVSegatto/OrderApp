package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.auth.AuthRequestDTO;
import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<Object>> login(@RequestBody AuthRequestDTO authRequest) {
    var response = authService.login(authRequest.getEmail(), authRequest.getPassword());
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<Object>> registerUser(
      @Valid @RequestBody CreateUserDTO createUserDTO) {
    var response = authService.register(createUserDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
  }

  @PostMapping("/logout")
  public ResponseEntity<ApiResponse<Object>> logout(User user) {
    authService.logout(user);
    return ResponseEntity.ok(ApiResponse.success());
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<ApiResponse<Object>> refreshToken(HttpServletRequest request) {
    var response = authService.refreshToken(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }
  // TODO future to consider - forgot password - reset password - verify email

}
