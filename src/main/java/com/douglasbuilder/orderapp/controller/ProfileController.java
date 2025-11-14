package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.profile.ProfileChangePasswordDTO;
import com.douglasbuilder.orderapp.dto.profile.ProfileResponseDTO;
import com.douglasbuilder.orderapp.dto.profile.ProfileUpdateDTO;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<ProfileResponseDTO> getProfile(User user) {
    return ResponseEntity.ok(userService.getProfile(user));
  }

  @PutMapping
  public ResponseEntity<ProfileResponseDTO> updateProfile(
      @Valid @RequestBody ProfileUpdateDTO dto, User user) {
    return ResponseEntity.ok(userService.updateProfile(user, dto));
  }

  @PutMapping("/password")
  public ResponseEntity<?> changePassword(
      @Valid @RequestBody ProfileChangePasswordDTO dto, User user) {
    userService.changePassword(dto, user);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<?> deleteAccount(User user) {
    userService.deleteAccount(user);
    return ResponseEntity.ok().build();
  }
}
