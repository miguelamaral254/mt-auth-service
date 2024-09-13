package br.com.loginauth.exceptions;

import br.com.loginauth.dto.ResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ResponseDTO(e.getMessage(), null));
    }

    // Add more exception handlers as needed
}
