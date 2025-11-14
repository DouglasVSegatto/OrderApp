package com.douglasbuilder.orderapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.douglasbuilder.orderapp.model.Token;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.TokenRepository;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
  private final TokenRepository tokenRepository;

  private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

  @Value(value = "${api.security.token.secret}")
  private String secret;

  @Value(value = "${application.security.token.access-token-expiration}")
  private int accessTokenExpireTime;

  @Value(value = "${application.security.token.refresh-token-expiration}")
  private int refreshTokenExpireTime;

  public TokenService(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

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
      token.setToken(tokenValue);
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

  public boolean isTokenValid(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWT.require(algorithm).withIssuer("auth-api").build().verify(token);
      return true;
    } catch (JWTVerificationException exception) {
      logger.warn("Token security issue: {}", exception.getMessage());
      return false;
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
}
