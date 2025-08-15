package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.dto.RequestUser;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

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
    public ResponseEntity<?> postUser(@RequestBody RequestUser requestUser){
        User newUser = new User();
        newUser.setId(new Random().nextLong());
        newUser.setFirstName(requestUser.getFirstName());
        newUser.setLastName(requestUser.getLastName());
        return ResponseEntity.status(201).body(newUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        return ResponseEntity.ok().build();
    }

}
