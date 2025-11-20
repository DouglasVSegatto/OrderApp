package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.profile.ProfileChangePasswordDTO;
import com.douglasbuilder.orderapp.dto.user.UpdateUserDTO;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.service.UserService;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<ApiResponse<Object>> getUsers() {
    return ResponseEntity.ok(new ApiResponse<>(userService.getAll()));
  }

  @GetMapping("/me")
  public ResponseEntity<ApiResponse<Object>> getUser() {
    return ResponseEntity.ok(new ApiResponse<>(userService.findByEmail()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteUserById(@PathVariable UUID id) {
    userService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>());
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> updateUserById(
      @PathVariable UUID id, @RequestBody UpdateUserDTO updateUserDTO) {
    userService.updateById(id, updateUserDTO);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  // UPDATE PROFILE
  @PutMapping("/first-name")
  public ResponseEntity<ApiResponse<Object>> updateFirstName(
          @RequestParam String firstName, User user) {
    userService.updateFirstName(firstName, user);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/last-name")
  public ResponseEntity<ApiResponse<Object>> updateLastName(
          @RequestParam String lastName, User user) {
    userService.updateLastName(lastName, user);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/email")
  public ResponseEntity<ApiResponse<Object>> updateEmail(
          @Email @RequestParam String email, User user) {
    userService.updateEmail(email, user);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/password")
  public ResponseEntity<ApiResponse<Object>> changePassword(
          @Valid @RequestBody ProfileChangePasswordDTO dto, User user) {
    userService.changePassword(dto, user);
    return ResponseEntity.ok(new ApiResponse<>());
  }


  //DELETE/DISABLE
  @DeleteMapping
  public ResponseEntity<ApiResponse<Object>> deleteAccount(User user) {
    userService.deleteAccount(user);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>());
  }
}
