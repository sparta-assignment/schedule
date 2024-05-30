package org.sparta.schedule.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.schedule.dto.TokenDto;
import org.sparta.schedule.dto.UserResDto;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginDto {
    private UserResDto user;
    private TokenDto token;
}
