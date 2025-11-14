package com.douglasbuilder.orderapp.service;

import com.douglasbuilder.orderapp.dto.auth.AuthResponseDTO;
import com.douglasbuilder.orderapp.dto.user.CreateUserDTO;
import com.douglasbuilder.orderapp.dto.user.ResponseUserDTO;
import com.douglasbuilder.orderapp.exceptions.auth.AuthInvalidCredentialsException;
import com.douglasbuilder.orderapp.exceptions.auth.AuthTokenExpiredException;
import com.douglasbuilder.orderapp.exceptions.user.DuplicateEmailException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import com.douglasbuilder.orderapp.model.Token;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.TokenRepository;
import com.douglasbuilder.orderapp.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final TokenService tokenService;
  private final TokenRepository tokenRepository;

  private void authenticateUser(String email, String pwd) {
    var userPassword = new UsernamePasswordAuthenticationToken(email, pwd);
    this.authenticationManager.authenticate(userPassword);
  }

  @Transactional
  public AuthResponseDTO login(String email, String password) {
    authenticateUser(email, password);

    var user = userService.findByEmail(email);

    tokenRepository.deleteAllByUser(user);

    var accessToken = tokenService.generateAccessToken(user);
    var refreshToken = tokenService.generateRefreshToken(user);
    saveRefreshToken(refreshToken);
    return new AuthResponseDTO(accessToken.getToken(), refreshToken.getToken(), "Login successful");
  }

  public ResponseUserDTO register(CreateUserDTO createUserDTO) {

    if (userService.emailExists(createUserDTO.getEmail())) {
      throw new DuplicateEmailException("User already exists.");
    }
    var user = userService.create(createUserDTO);
    return new ResponseUserDTO(user.getFullName(), user.getEmail(), "Registered successful");
  }

  private void saveRefreshToken(Token token) {
    tokenRepository.save(token);
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
      return userService.findByEmail(authentication.getName());
    }
    throw new UserNotFoundException("No authenticated user found");
  }

  @Transactional
  public AuthResponseDTO refreshToken(HttpServletRequest request) {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new AuthInvalidCredentialsException("Missing or invalid authorization header");
    }

    String refreshToken = authHeader.replace("Bearer ", "");

    if (!tokenService.isTokenValid(refreshToken)) {
      throw new AuthTokenExpiredException("Refresh Token has expired");
    }

    String email = tokenService.extractTokenSubject(refreshToken);
    User user = userService.findByEmail(email);

    tokenRepository.deleteAllByUser(user);

    Token newAccessToken = tokenService.generateAccessToken(user);
    Token newRefreshToken = tokenService.generateRefreshToken(user);
    saveRefreshToken(newRefreshToken);

    return new AuthResponseDTO(
        newAccessToken.getToken(), newRefreshToken.getToken(), "Tokens refreshed successfully");
  }

  @Transactional
  public void logout() {
    tokenRepository.deleteAllByUser(getCurrentUser());
  }
}
