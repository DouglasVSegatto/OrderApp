package com.douglasbuilder.orderapp.dto.profile;

import lombok.Data;

@Data
public class ProfileUpdateDTO {
  private String firstName;
  private String lastName;
  private String email;
  private String profilePicture;
}
