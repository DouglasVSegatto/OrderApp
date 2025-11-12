package com.douglasbuilder.orderapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.douglasbuilder.orderapp.model.Token;
import com.douglasbuilder.orderapp.model.User;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  // TODO update to real expected time after tests

  int SECONDS_TO_EXPIRE = 1800;
  private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

  @Value("${api.security.token.secret}")
  private String secret;

  @Value("${application.security.token.access-token-expiration}")
  private int accessTokenExpireTime;

  @Value("${application.security.token.refresh-token-expiration}")
  private int refreshTokenExpireTime;

  private Token generateToken(User user, int expirationTime) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      Instant expiresAt = getExpirationDate(expirationTime);
      String tokenValue =
          JWT.create()
              .withIssuer("auth-api")
              .withSubject(user.getEmail())
              .withExpiresAt(expiresAt)
              .sign(algorithm);

      Token token = new Token();
      token.setRefreshToken(tokenValue);
      token.setExpiresAt(expiresAt);
      token.setUser(user);
      return token;
    } catch (JWTCreationException exception) {
      throw new RuntimeException("Error while generating token", exception);
    }
  }

  public Token generateRefreshToken(User user) {
    return generateToken(user, refreshTokenExpireTime);
  }

  public Token generateAccessToken(User user) {
    return generateToken(user, accessTokenExpireTime);
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();
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

  private Instant getExpirationDate(int expirationTime) {
    return Instant.now().plusSeconds(expirationTime);
  }

  public void deleteTokens(String Token) {}

  public void deleteUserTokens(User user) {}
}
