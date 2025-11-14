package com.douglasbuilder.orderapp.dto.profile;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProfileResponseDTO {
  private String firstName;
  private String lastName;
  private String fullName;
  private String email;
  private String profilePicture;
  private LocalDateTime createdAt;
  private LocalDateTime lastLogin;
}
