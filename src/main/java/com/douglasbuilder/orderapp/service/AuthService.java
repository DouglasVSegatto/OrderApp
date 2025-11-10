package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.ResponseUserDTO;
import com.douglasbuilder.orderapp.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserService userService;
  @Autowired private TokenService tokenService;

  public void authenticateUser(String email, String pwd) {
    var userPassword = new UsernamePasswordAuthenticationToken(email, pwd);
    this.authenticationManager.authenticate(userPassword);
  }

  public String login(String email, String password) {
    authenticateUser(email, password);
    var user = userService.findByEmail(email);
    return tokenService.generateToken(user);
  }

  public ResponseUserDTO register(CreateUserDTO createUserDTO) {
    return userService.create(createUserDTO);
  }
}
