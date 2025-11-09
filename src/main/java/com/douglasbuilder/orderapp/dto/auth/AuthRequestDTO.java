package com.douglasbuilder.orderapp.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {

  @Email
  private String email;
  private String password;

  public void setEmail(String email) {
    this.email = email.toLowerCase();
  }
}
