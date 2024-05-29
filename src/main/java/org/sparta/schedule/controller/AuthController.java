package org.sparta.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.schedule.dto.LoginReqDto;
import org.sparta.schedule.dto.LoginResDto;
import org.sparta.schedule.dto.UserAddDto;
import org.sparta.schedule.dto.UserResDto;
import org.sparta.schedule.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 API", description = "인증 API 입니다.")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping
    public UserResDto signUp(@RequestBody @Valid UserAddDto userAddDto) {
        return authService.signUp(userAddDto);
    }


    @Operation(summary = "로그인", description = "로그인")
    @PostMapping("/signIn")
    public LoginResDto signIn(@RequestBody LoginReqDto loginReqDto) {
        return authService.signIn(loginReqDto);
    }
}
