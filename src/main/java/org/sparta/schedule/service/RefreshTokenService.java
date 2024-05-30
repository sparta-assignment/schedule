package org.sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.dto.TokenDto;
import org.sparta.schedule.entity.RefreshToken;
import org.sparta.schedule.entity.User;
import org.sparta.schedule.repository.RefreshTokenRepository;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken addRefreshToken(User user, String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByUser_Id(user.getId());
        if (token != null) {
            token.updateToken(refreshToken);
            return refreshTokenRepository.save(token);
        }
        token = RefreshToken.builder()
                .user(user)
                .token(refreshToken)
                .build();
        return refreshTokenRepository.save(token);
    }
}
