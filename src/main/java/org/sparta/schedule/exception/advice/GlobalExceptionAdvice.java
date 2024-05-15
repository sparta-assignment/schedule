package org.sparta.schedule.exception.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.sparta.schedule.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse defaultException(HttpServletRequest request, Exception e){
        return ExceptionResponse.builder()
                .msg(e.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ExceptionResponse> customException(HttpServletRequest request, CustomException e){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .msg(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(exceptionResponse, null, e.getStatus());
    }
}
