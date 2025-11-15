package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.profile.ProfileChangePasswordDTO;
import com.douglasbuilder.orderapp.dto.profile.ProfileResponseDTO;
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
  public ResponseEntity<ProfileResponseDTO> getProfile(User user) {
    return ResponseEntity.ok(userService.getProfile(user));
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

  //UPDATE PROFILE
  @PutMapping("/first-name")
  public ResponseEntity<ProfileResponseDTO> updateFirstName(
          @Valid @RequestParam String firstName, User user) {
    userService.updateFirstName(firstName, user);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/last-name")
  public ResponseEntity<ProfileResponseDTO> updateLastName(
          @Valid @RequestParam String lastName, User user) {
    userService.updateLastName(lastName, user);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/email")
  public ResponseEntity<ProfileResponseDTO> updateEmail(
          @Valid @Email @RequestParam String email, User user) {
    userService.updateEmail(email, user);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/picture")
  public ResponseEntity<ProfileResponseDTO> updatePicture(
          @Valid @RequestParam String url, User user) {
    userService.updateProfilePicture(url, user);
    return ResponseEntity.ok().build();
  }
}
