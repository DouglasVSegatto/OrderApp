package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.auth.AuthRequestDTO;
import com.douglasbuilder.orderapp.dto.auth.LoginResponseDTO;
import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  @Autowired private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
    String token = authService.login(authRequest.getEmail(), authRequest.getPassword());
    return ResponseEntity.ok(new LoginResponseDTO(token, "Bearer"));
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
    var user = authService.register(createUserDTO);
    return ResponseEntity.status(201).body(new ApiResponse<>(user));
  }

  @PostMapping("/refreshToken")
  public ResponseEntity<?> refreshToken() {
    return null;
  }
}
