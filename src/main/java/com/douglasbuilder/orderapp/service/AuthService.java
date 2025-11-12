package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.auth.AuthResponseDTO;
import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.exceptions.user.DuplicateEmailException;
import com.douglasbuilder.orderapp.model.Token;
import com.douglasbuilder.orderapp.repository.TokenRepository;
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
  @Autowired private TokenRepository tokenRepository;

  private void authenticateUser(String email, String pwd) {
    var userPassword = new UsernamePasswordAuthenticationToken(email, pwd);
    this.authenticationManager.authenticate(userPassword);
  }

  public AuthResponseDTO login(String email, String password) {
    authenticateUser(email, password);

    var user = userService.findByEmail(email);

    var accessToken = tokenService.generateAccessToken(user);
    var refreshToken = tokenService.generateRefreshToken(user);
    saveRefreshToken(refreshToken);
    // TODO RevokeAllTokenByUser --
    return new AuthResponseDTO(accessToken, refreshToken, "Login successful");
  }

  public AuthResponseDTO register(CreateUserDTO createUserDTO) {

    if (userService.emailExists(createUserDTO.getEmail())) {
      throw new DuplicateEmailException("User already exists.");
    }
    var user = userService.create(createUserDTO);
    var accessToken = tokenService.generateAccessToken(user);
    var refreshToken = tokenService.generateRefreshToken(user);
    saveRefreshToken(refreshToken);

    return new AuthResponseDTO(accessToken, refreshToken, "Registered successful");
  }

  private void saveRefreshToken(Token token) {
    tokenRepository.save(token);
  }
}
