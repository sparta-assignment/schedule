package org.sparta.schedule.common.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sparta.schedule.common.response.CommonResult;
import org.sparta.schedule.common.response.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    private final ResponseService responseService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("401 Unauthorized, {}", request.getRequestURI());
        CommonResult result = responseService.getFailResult(HttpStatus.UNAUTHORIZED.value(), "잘못된 토큰이거나 만료된 토큰입니다.");
        responseService.setErrorResponse(response, result);
    }
}
