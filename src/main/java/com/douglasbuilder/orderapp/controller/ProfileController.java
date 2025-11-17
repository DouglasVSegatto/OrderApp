package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.profile.ProfileChangePasswordDTO;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<ApiResponse<Object>> getProfile(User user) {
    var response = userService.getProfile(user);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  @PutMapping("/password")
  public ResponseEntity<ApiResponse<Object>> changePassword(
      @Valid @RequestBody ProfileChangePasswordDTO dto, User user) {
    userService.changePassword(dto, user);
    return ResponseEntity.ok(ApiResponse.success());
  }

  @DeleteMapping
  public ResponseEntity<ApiResponse<Object>> deleteAccount(User user) {
    userService.deleteAccount(user);
    return ResponseEntity.ok(ApiResponse.success());
  }

  // UPDATE PROFILE
  @PutMapping("/first-name")
  public ResponseEntity<ApiResponse<Object>> updateFirstName(
      @RequestParam String firstName, User user) {
    userService.updateFirstName(firstName, user);
    return ResponseEntity.ok(ApiResponse.success());
  }

  @PutMapping("/last-name")
  public ResponseEntity<ApiResponse<Object>> updateLastName(
      @RequestParam String lastName, User user) {
    userService.updateLastName(lastName, user);
    return ResponseEntity.ok(ApiResponse.success());
  }

  @PutMapping("/email")
  public ResponseEntity<ApiResponse<Object>> updateEmail(
      @Email @RequestParam String email, User user) {
    userService.updateEmail(email, user);
    return ResponseEntity.ok(ApiResponse.success());
  }

  @PutMapping("/picture")
  public ResponseEntity<ApiResponse<Object>> updatePicture(@RequestParam String url, User user) {
    userService.updateProfilePicture(url, user);
    return ResponseEntity.ok(ApiResponse.success());
  }
}
