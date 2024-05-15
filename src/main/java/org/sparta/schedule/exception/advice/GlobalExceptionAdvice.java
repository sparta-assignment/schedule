package org.sparta.schedule.exception.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.sparta.schedule.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * validation 예외 처리
     * not null 필드일 경우 not null 메시지를 붙여서 리턴한다.
     * @param exception advice 파라미터
     * @return 입력 값 검증 오류 내용
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> processValidationError(HttpServletRequest request, MethodArgumentNotValidException exception) {
        int VALIDATION_ERROR_STATUS = 400;
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder builder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String fieldName = fieldError.getField();

            builder.append("[");
            builder.append(fieldName);
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }

        String msg = builder.toString();

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .msg(msg)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(exceptionResponse, null, VALIDATION_ERROR_STATUS);
    }
}
