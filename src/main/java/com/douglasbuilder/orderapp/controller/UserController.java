package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.UpdateUserDTO;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO){
        User user = userService.create(createUserDTO);
        return ResponseEntity.status(201).body(user);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        //TODO try catch
        // return fail and success msg
        userService.delete(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody UpdateUserDTO updateUserDTO){
        //TODO try catch
        // return fail and success msg
        User user = userService.update(email, updateUserDTO);
        return ResponseEntity.ok().body(user);
    }


}
