package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.auth.AuthRequestDTO;
import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  @Autowired private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest) {
    return ResponseEntity.ok()
        .body(authService.login(authRequest.getEmail(), authRequest.getPassword()));
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
    return ResponseEntity.status(201).body(authService.register(createUserDTO));
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout() {
    authService.logout();
    return ResponseEntity.ok()
        .body("User " + authService.getCurrentUser().getEmail() + "logged out");
  }

  @PostMapping("/refreshToken")
  public ResponseEntity<?> refreshToken(HttpServletRequest request) {
    return ResponseEntity.ok().body(authService.refreshToken(request));
  }
  // TODO future to consider - forgot password - reset password - verify email

}
