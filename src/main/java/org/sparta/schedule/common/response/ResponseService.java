package org.sparta.schedule.common.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ResponseService {

    public CommonResult getFailResult(int code, String msg) {
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
