package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.user.UpdateUserDTO;
import com.douglasbuilder.orderapp.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<ApiResponse<Object>> getUsers() {
    var users = userService.getAll();
    return ResponseEntity.ok(ApiResponse.success(users));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> findUserById(@PathVariable UUID id) {
    var user = userService.findById(id);
    return ResponseEntity.ok(ApiResponse.success(user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> deleteUserById(@PathVariable UUID id) {
    userService.deleteById(id);
    return ResponseEntity.ok(ApiResponse.success());
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Object>> updateUserById(
      @PathVariable UUID id, @RequestBody UpdateUserDTO updateUserDTO) {
    userService.updateById(id, updateUserDTO);
    return ResponseEntity.ok(ApiResponse.success());
  }
}
