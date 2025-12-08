package com.douglasbuilder.orderapp.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
  /*{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "message": "Login successful"
  }
  */
  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("message")
  private String message;

}
