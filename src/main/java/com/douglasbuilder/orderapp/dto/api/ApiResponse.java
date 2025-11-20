package com.douglasbuilder.orderapp.dto.api;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ApiResponse<T> {
  private T data;
  private LocalDateTime timestamp;

  public ApiResponse(T data){
    this.data = data;
    this.timestamp = LocalDateTime.now();
  }

  public ApiResponse(){
    this.timestamp = LocalDateTime.now();
  }
}
