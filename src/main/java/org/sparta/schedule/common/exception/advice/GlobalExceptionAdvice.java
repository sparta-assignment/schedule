package org.sparta.schedule.common.exception.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.sparta.schedule.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionResponse defaultException(HttpServletRequest request, Exception e){
        String msg = "실패하였습니다.";
        if(e instanceof NoResourceFoundException) {
            msg = "해당하는 리소스가 없습니다.";
        }
        return ExceptionResponse.builder()
                .msg(msg)
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
        int VALIDATION_ERROR_STATUS = HttpStatus.BAD_REQUEST.value();
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder builder = new StringBuilder();

        FieldError fieldError = bindingResult.getFieldErrors().get(0);
        builder.append("[");
        builder.append(fieldError.getField());
        builder.append("](은)는 ");
        builder.append(fieldError.getDefaultMessage() == ""? "데이터 검증에 실패하였습니다." : fieldError.getDefaultMessage());
        if (checkPrimitiveType(fieldError.getRejectedValue())) {
            builder.append("   입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .msg(builder.toString())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(exceptionResponse, null, VALIDATION_ERROR_STATUS);
    }

    private boolean checkPrimitiveType(Object value) {
        return value instanceof String || value instanceof Integer || value instanceof Float || value instanceof Double;
    }
}
