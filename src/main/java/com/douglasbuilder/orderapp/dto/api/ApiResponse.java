package com.douglasbuilder.orderapp.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  private boolean success;
  private T data;
  private LocalDateTime timestamp;

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(true, data, LocalDateTime.now());
  }

  public static <T> ApiResponse<T> success() {
    return new ApiResponse<>(true, null, LocalDateTime.now());
  }
}
