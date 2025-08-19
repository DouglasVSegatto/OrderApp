package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.dto.user.RequestUserDTO;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody RequestUserDTO requestUserDTO){
        User newUser = new User();
        newUser.setId(new Random().nextLong());
        newUser.setFirstName(requestUserDTO.getFirstName());
        newUser.setLastName(requestUserDTO.getLastName());
        return ResponseEntity.status(201).body(newUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        return ResponseEntity.ok().build();
    }

}
