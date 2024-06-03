package org.sparta.schedule.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.schedule.common.dto.CommonDto;
import org.sparta.schedule.dto.token.TokenDto;
import org.sparta.schedule.dto.user.UserResDto;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginDto implements CommonDto {
    private UserResDto user;
    private TokenDto token;
}
