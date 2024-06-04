package org.sparta.schedule.dto.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.schedule.common.dto.CommonDto;

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
