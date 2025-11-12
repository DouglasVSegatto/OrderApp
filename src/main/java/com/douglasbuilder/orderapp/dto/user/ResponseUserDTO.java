package com.douglasbuilder.orderapp.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDTO {
  @JsonProperty("full_name")
  private String fullName;

  @JsonProperty("email")
  private String email;

  @JsonProperty("message")
  private String message;
}
