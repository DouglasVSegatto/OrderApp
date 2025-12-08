package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.user.AddressUpdateDTO;
import com.douglasbuilder.orderapp.dto.user.UpdatePhoneNumberDTO;
import com.douglasbuilder.orderapp.dto.user.UserChangePasswordDTO;
import com.douglasbuilder.orderapp.service.UserService;
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
  public ResponseEntity<ApiResponse<Object>> getUserProfile() {
    return ResponseEntity.ok(new ApiResponse<>(userService.getUserProfile()));
  }

  
  // UPDATE PROFILE
  @PutMapping("/first-name")
  public ResponseEntity<ApiResponse<Object>> updateFirstName(
          @RequestParam String firstName) {
    userService.updateFirstName(firstName);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/last-name")
  public ResponseEntity<ApiResponse<Object>> updateLastName(
          @RequestParam String lastName) {
    userService.updateLastName(lastName);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/email")
  public ResponseEntity<ApiResponse<Object>> updateEmail(
          @Email @RequestParam String email) {
    userService.updateEmail(email);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/password")
  public ResponseEntity<ApiResponse<Object>> changePassword(
          @Valid @RequestBody UserChangePasswordDTO dto) {
    userService.changePassword(dto);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/address")
  public ResponseEntity<ApiResponse<Object>> updateAddress(
          @Valid @RequestBody AddressUpdateDTO dto) {
    userService.updateAddress(dto);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  @PutMapping("/phone_number")
  public ResponseEntity<ApiResponse<Object>> updatePhoneNumber(
          @Valid @RequestBody UpdatePhoneNumberDTO dto) {
    userService.updatePhoneNumber(dto);
    return ResponseEntity.ok(new ApiResponse<>());
  }

  //DELETE/DISABLE
  //TODO User token still works, FUTURE: to implement isLoggedOut to user to track.
  @DeleteMapping
  public ResponseEntity<ApiResponse<Object>> deleteAccount() {
    userService.deleteAccount();
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>());
  }
}
