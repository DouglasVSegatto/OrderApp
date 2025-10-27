package com.douglasbuilder.orderapp.dto.api;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorDTO {

  private LocalDateTime timestamp;
  private String message;
  private String details;
}
