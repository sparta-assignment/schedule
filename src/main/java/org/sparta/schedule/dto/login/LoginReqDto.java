package org.sparta.schedule.dto.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginReqDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}