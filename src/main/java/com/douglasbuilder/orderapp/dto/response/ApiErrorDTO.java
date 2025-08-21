package com.douglasbuilder.orderapp.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiErrorDTO {

  private LocalDateTime timestamp;
  private String message;
  private String details;
}
