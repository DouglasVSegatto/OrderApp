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

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
    User user = userService.create(createUserDTO);
    return ResponseEntity.status(201).body(user);
  }

  @GetMapping
  public List<User> getUsers() {
    return userService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> findUserById(@PathVariable Long id) {
    User user = userService.findById(id);
    return ResponseEntity.status(HttpStatus.FOUND).body(user);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
    userService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUserById(
          @PathVariable Long id, @RequestBody UpdateUserDTO updateUserDTO) {
    User user = userService.updateById(id, updateUserDTO);
    return ResponseEntity.ok().body(user);
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
    User user = userService.findByEmail(email);
    return ResponseEntity.status(HttpStatus.FOUND).body(user);
  }

  @DeleteMapping("/email/{email}")
  public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
    userService.deleteByEmail(email);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/email/{email}")
  public ResponseEntity<?> updateUserByEmail(
      @PathVariable String email, @RequestBody UpdateUserDTO updateUserDTO) {
    User user = userService.updateByEmail(email, updateUserDTO);
    return ResponseEntity.ok().body(user);
  }
}
