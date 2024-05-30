package org.sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.common.exception.CustomException;
import org.sparta.schedule.common.jwt.JwtTokenProvider;
import org.sparta.schedule.dto.TokenDto;
import org.sparta.schedule.entity.User;
import org.sparta.schedule.entity.UserRoleEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final long REFRESH_EXPIRATION_TIME = 12 * 60 * 60;   // 12시간
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    public String createAccessToken(String username, UserRoleEnum role) {
        return jwtTokenProvider.createToken(username, role);
    }

    /**
     * refresh token의 만료시간을 가진 refresh 토큰을 생성하고 db에 저장한다.
     *
     * @param user 유저 정보
     * @return refresh token 반환
     */
    public String createRefreshToken(User user) {
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole(), REFRESH_EXPIRATION_TIME);
        refreshTokenService.addRefreshToken(user, token);
        return token;
    }

    public TokenDto reissueToken(User user, String refreshToken) {
        // 1. refresh 토큰 유효성 확인
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "토큰 유효성 검증에 실패했습니다.");
        }
        // 2. accessToken의 유저와 refreshToken의 유저가 동일한지 확인
        checkUsername(user.getUsername(), jwtTokenProvider.getUsername(refreshToken));

        // 3. 토큰 및 리프레시 토큰 재발급
        String newAccessToken = createAccessToken(user.getUsername(), user.getRole());
        String newRefreshToken = createRefreshToken(user);
        return new TokenDto(newAccessToken, newRefreshToken);
    }

    public String getTokenUsername(String token) {
        return jwtTokenProvider.getPreAccessTokenUsername(token);
    }

    public void checkUsername(String inputUsername, String username) {
        if (!Objects.equals(inputUsername, username)) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "토큰 유저 검증에 실패했습니다.");
        }
    }
}
