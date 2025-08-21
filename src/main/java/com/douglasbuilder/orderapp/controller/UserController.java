package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.UpdateUserDTO;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.service.UserService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getUsers() {
    return userService.getAll();
  }

  @GetMapping("/{email}")
  public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
    User user = userService.getByEmail(email);
    return ResponseEntity.status(HttpStatus.FOUND).body(user);
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
    User user = userService.create(createUserDTO);
    return ResponseEntity.status(201).body(user);
  }

  @DeleteMapping("/{email}")
  public ResponseEntity<?> deleteUser(@PathVariable String email) {
    userService.delete(email);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{email}")
  public ResponseEntity<?> updateUser(
      @PathVariable String email, @RequestBody UpdateUserDTO updateUserDTO) {
    User user = userService.update(email, updateUserDTO);
    return ResponseEntity.ok().body(user);
  }
}
