package com.douglasbuilder.orderapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ApiErrorDTO {

    private LocalDateTime timestamp;
    private String message;
    private String details;

}
