package org.sparta.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenDto {
    private String accessToken;
    private String refreshToken;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class RefreshToken {
        private String refreshToken;
    }
}
