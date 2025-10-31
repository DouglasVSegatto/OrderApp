package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.dto.api.ApiResponse;
import com.douglasbuilder.orderapp.dto.auth.AuthRequestDTO;
import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.model.Order;
import com.douglasbuilder.orderapp.security.TokenService;
import com.douglasbuilder.orderapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequest){
        var user = userService.authenticateUser(authRequest.getEmail(), authRequest.getPassword());
        return ResponseEntity.ok().body(tokenService.generateToken(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody CreateUserDTO createUserDTO) {
        var user = userService.create(createUserDTO);
        return ResponseEntity.status(201).body(new ApiResponse<>(user));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(){
        return null;
    }

}
