package com.douglasbuilder.orderapp.controller;

import com.douglasbuilder.orderapp.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    /*
    POST /auth/register
    POST /auth/login
    POST /auth/refresh
     */
    @PostMapping("/login")
    public String login(@RequestBody )

}
