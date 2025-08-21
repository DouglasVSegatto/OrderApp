package com.douglasbuilder.orderapp.exceptions;

import com.douglasbuilder.orderapp.dto.response.ApiErrorDTO;
import com.douglasbuilder.orderapp.exceptions.user.DuplicateEmailException;
import com.douglasbuilder.orderapp.exceptions.user.UserException;
import com.douglasbuilder.orderapp.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleUserNotFound(UserNotFoundException e){
        ApiErrorDTO error = new ApiErrorDTO(
                LocalDateTime.now(),
                "User not Found",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiErrorDTO> handleDuplicateEmailException(DuplicateEmailException e){
        ApiErrorDTO error = new ApiErrorDTO(
                LocalDateTime.now(),
                "Email already in use.",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiErrorDTO> handleUserException(UserException e){
        ApiErrorDTO error = new ApiErrorDTO(
                LocalDateTime.now(),
                "Internal Error",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }
}
