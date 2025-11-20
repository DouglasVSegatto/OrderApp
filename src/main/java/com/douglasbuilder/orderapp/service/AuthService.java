package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.auth.AuthResponseDTO;
import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.ResponseUserDTO;
import com.douglasbuilder.orderapp.exceptions.auth.AuthInvalidCredentialsException;
import com.douglasbuilder.orderapp.exceptions.auth.AuthTokenExpiredException;
import com.douglasbuilder.orderapp.exceptions.user.DuplicateEmailException;
import com.douglasbuilder.orderapp.model.Token;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.TokenRepository;
import com.douglasbuilder.orderapp.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final TokenService tokenService;
  private final TokenRepository tokenRepository;
  private final CurrentUserService currentUser;

  private void authenticateUser(String email, String pwd) {
    var userPassword = new UsernamePasswordAuthenticationToken(email, pwd);
    this.authenticationManager.authenticate(userPassword);
  }

  @Transactional
  public AuthResponseDTO login(String email, String password) {
    authenticateUser(email, password);

    var user = userService.getUser(email);

    deleteUserTokens();

    var accessToken = tokenService.generateAccessToken(user);
    var refreshToken = tokenService.generateRefreshToken(user);
    saveRefreshToken(refreshToken);

    user.setLastLogin(LocalDateTime.now());

    userService.recordLogin(user);

    return new AuthResponseDTO(accessToken.getToken(), refreshToken.getToken(), "Login successful");
  }

  public ResponseUserDTO register(CreateUserDTO createUserDTO) {

    if (userService.emailExists(createUserDTO.getEmail())) {
      throw new DuplicateEmailException("Email already in use.");
    }
    var user = userService.create(createUserDTO);
    return new ResponseUserDTO(user.getFullName(), user.getEmail(), "Registered successful");
  }

  private void saveRefreshToken(Token token) {
    tokenRepository.save(token);
  }

  @Transactional
  public AuthResponseDTO refreshToken(HttpServletRequest request) {
    var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new AuthInvalidCredentialsException("Missing or invalid authorization header");
    }

    var refreshToken = authHeader.replace("Bearer ", "");

    var email = tokenService.getValidRefreshTokenSubject(refreshToken);


    log.info("EMAIL RETURNED: " + email);
    if (email == null) {
      throw new AuthTokenExpiredException("Refresh Token has expired or is invalid");
    }

    User user = userService.getUser(email);

    deleteUserTokens();

    Token newAccessToken = tokenService.generateAccessToken(user);
    Token newRefreshToken = tokenService.generateRefreshToken(user);
    saveRefreshToken(newRefreshToken);

    return new AuthResponseDTO(
        newAccessToken.getToken(), newRefreshToken.getToken(), "Tokens refreshed successfully");
  }

  @Transactional
  public void logout() {
    deleteUserTokens();
  }

  @Transactional
  public void deleteUserTokens() {
    tokenRepository.deleteAllByUserEmail(currentUser.getCurrentUserEmail());
  }
}
