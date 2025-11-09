package com.douglasbuilder.orderapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.douglasbuilder.orderapp.model.User;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  int EXPIRATION_TIME_SEC = 7200;
  private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(User user) {
    try {
      logger.warn("Token Generate at: " + Instant.now());
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer("auth-api")
          .withSubject(user.getEmail())
          .withExpiresAt(getExpirationDate())
          .sign(algorithm);
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error while generating token", exception);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String subject =
          JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();
      return subject;
    } catch (TokenExpiredException exception) {
      logger.warn("Token expired for request: {}", exception.getMessage());
      return "";
    } catch (JWTVerificationException exception) {
      logger.warn("Invalid token provided: {}", exception.getMessage());
      return "";
    }
  }

  public boolean isTokenExpired(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWT.require(algorithm).withIssuer("auth-api").build().verify(token);
      return false;
    } catch (TokenExpiredException exception) {
      return true;
    } catch (JWTVerificationException e) {
      throw new RuntimeException("Invalid token signature or format", e);
    }
  }

  public String extractTokenSubject(String token) {
    try {
      return JWT.decode(token).getSubject();
    } catch (JWTDecodeException exception) {
      throw new RuntimeException("Invalid token format", exception);
    }
  }

  private Instant getExpirationDate() {
    return Instant.now().plusSeconds(EXPIRATION_TIME_SEC);
  }
}
