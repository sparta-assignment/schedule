package org.sparta.schedule.common.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ResponseService {

    public <T> ResponseEntity<ListResult<T>> getListResult(List<T> data){
        ListResult<T> response = new ListResult<>(data);
        setSuccessResult(response);
        return getResponseEntity(response, null);
    }

    public <T> ResponseEntity<ListResult<T>> getListResult(List<T> data, HttpHeaders headers){
        ListResult<T> response = new ListResult<>(data);
        setSuccessResult(response);
        return getResponseEntity(response, headers);
    }

    public <T> ResponseEntity<SingleResult<T>> getSingleResult(T data){
        SingleResult<T> response = new SingleResult<>(data);
        setSuccessResult(response);
        return getResponseEntity(response, null);
    }

    public <T> ResponseEntity<SingleResult<T>> getSingleResult(T data, HttpHeaders headers){
        SingleResult<T> response = new SingleResult<>(data);
        setSuccessResult(response);
        return getResponseEntity(response, headers);
    }

    private <T extends CommonResult> ResponseEntity<T> getResponseEntity(T data, HttpHeaders headers){
        return new ResponseEntity<T>(data, headers, HttpStatus.OK.value());
    }

    public void setSuccessResult(CommonResult result) {
        result.setSuccessResult();
    }

    public void setFailureResult(CommonResult result) {
        result.setFailureResult();
    }

    public CommonResult setFailureResult(int code, String msg) {
        return CommonResult.builder()
                .success(false)
                .code(code)
                .msg(msg)
                .build();
    }

    public void setErrorResponse(HttpServletResponse response, CommonResult result) throws IOException {
        ObjectMapper objectMapper= new ObjectMapper();
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(result.getCode());
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        CommonResult.builder()
                                .success(result.isSuccess())
                                .msg(result.getMsg())
                                .code(result.getCode())
                                .build()
                )
        );
    }
}
