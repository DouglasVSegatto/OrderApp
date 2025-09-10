package com.douglasbuilder.orderapp.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiErrorDTO {

    private LocalDateTime timestamp;
    private String message;
    private String details;
}
