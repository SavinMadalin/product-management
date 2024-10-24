package com.example.product.handler;

import com.example.product.model.exception.ErrorDto;
import com.example.product.model.exception.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final String DOCUMENTATION_LINK = "http://localhost:8080/swager/ui/index.html";

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder()
                        .errorCode(500)
                        .message(exception.getMessage())
                        .help(DOCUMENTATION_LINK).build());
    }

    @ExceptionHandler({GenericException.class})
    public ResponseEntity<ErrorDto> handleGenericException(GenericException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(ErrorDto.builder()
                        .errorCode(exception.getStatusCode().value())
                        .message(exception.getMessage())
                        .help(DOCUMENTATION_LINK).build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(ErrorDto.builder()
                        .errorCode(exception.getStatusCode().value())
                        .message(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage())
                        .help(DOCUMENTATION_LINK).build());
    }
}
