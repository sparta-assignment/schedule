package org.sparta.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.schedule.common.jwt.JwtTokenProvider;
import org.sparta.schedule.common.response.ResponseService;
import org.sparta.schedule.common.response.SingleResult;
import org.sparta.schedule.dto.login.LoginDto;
import org.sparta.schedule.dto.login.LoginReqDto;
import org.sparta.schedule.dto.login.LoginResDto;
import org.sparta.schedule.dto.token.TokenDto;
import org.sparta.schedule.dto.user.UserAddDto;
import org.sparta.schedule.dto.user.UserResDto;
import org.sparta.schedule.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 API", description = "인증 API 입니다.")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ResponseService responseService;

    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping
    public UserResDto signUp(@RequestBody @Valid UserAddDto userAddDto) {
        return authService.signUp(userAddDto);
    }

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<SingleResult<LoginResDto>> signIn(@RequestBody LoginReqDto loginReqDto) {
        LoginDto loginDto = authService.signIn(loginReqDto);

        return responseService.getSingleResult(
                LoginResDto.builder()
                .user(loginDto.getUser())
                .refreshToken(loginDto.getToken().getRefreshToken())
                .build(),
                getAuthorizationHeader(loginDto.getToken().getAccessToken())
        );
    }

    @Operation(summary = "토큰 재발급", description = "리프레시 토큰으로 토큰을 재발급 합니다.")
    @PostMapping("token")
    public ResponseEntity<SingleResult<TokenDto.RefreshToken>> reissueToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
                                                              @RequestBody TokenDto.RefreshToken tokenDto) {
        accessToken = JwtTokenProvider.getTokenFromRequest(accessToken);
        TokenDto token = authService.reissueToken(accessToken, tokenDto.getRefreshToken());
        return responseService.getSingleResult(
                new TokenDto.RefreshToken(token.getRefreshToken()),
                getAuthorizationHeader(token.getAccessToken())
        );
    }

    private HttpHeaders getAuthorizationHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return headers;
    }
}
