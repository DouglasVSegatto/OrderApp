package com.douglasbuilder.orderapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.douglasbuilder.orderapp.model.Token;
import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.model.enumetations.TokenType;
import com.douglasbuilder.orderapp.repository.TokenRepository;
import java.time.Instant;

import com.douglasbuilder.orderapp.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
  private final TokenRepository tokenRepository;
  private final CurrentUserService currentUser;

  @Value("${api.security.token.secret}")
  private String secret;

  @Value("${application.security.token.access-token-expiration}")
  private int accessTokenExpireTime;

  @Value("${application.security.token.refresh-token-expiration}")
  private int refreshTokenExpireTime;

  private Algorithm getAlgorithm() {
    return Algorithm.HMAC256(secret);
  }

  private Token generateToken(User user, int expirationTime, String tokenType) {
    try {
      var algorithm = getAlgorithm();
      Instant expiresAt = getExpirationDate(expirationTime);
      String tokenValue =
          JWT.create()
              .withIssuer("auth-api")
              .withSubject(user.getEmail())
              .withClaim("type", tokenType)
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
    return generateToken(user, refreshTokenExpireTime, TokenType.REFRESH.toString());
  }

  public Token generateAccessToken(User user) {
    return generateToken(user, accessTokenExpireTime, TokenType.ACCESS.toString());
  }

  private DecodedJWT getVerifiedJwt(String token) throws JWTVerificationException {
    var algorithm = getAlgorithm();
    return JWT.require(algorithm).withIssuer("auth-api").build().verify(token);
  }

  public String getValidRefreshTokenSubject(String token) {
    try {

      DecodedJWT verifiedJWT = getVerifiedJwt(token);

      if (!isRefreshToken(verifiedJWT) || !tokenRepository.existsByToken(token)) {
        logger.warn("Attempted use of non-existent refresh token.");
        return null;
      }
      return verifiedJWT.getSubject();

    } catch (JWTVerificationException exception) {
      logger.warn("Token Validation Failed: {}", exception.getMessage());
      return null;
    }
  }

  public String getValidAccessTokenSubject(String token) {
    try {
      DecodedJWT verifiedJWT = getVerifiedJwt(token);
      if (isRefreshToken(verifiedJWT)) {
        logger.warn("Attempted use of refresh token.");
        return null;
      }
      return verifiedJWT.getSubject();
    } catch (JWTVerificationException exception) {
      logger.warn("Token Validation Failed: {}", exception.getMessage());
      return null;
    }
  }

  private Instant getExpirationDate(int expirationTime) {
    return Instant.now().plusSeconds(expirationTime);
  }

  public boolean isRefreshToken(DecodedJWT decodedJWT) {
    return TokenType.REFRESH.toString().equals(decodedJWT.getClaim("type").asString());
  }

  public void deleteUSerToken(String email){
    tokenRepository.deleteAllByUserEmail(email);
  }

  public void deleteUSerToken(){
    tokenRepository.deleteAllByUserEmail(currentUser.getCurrentUserEmail());
  }
}
