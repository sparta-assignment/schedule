package org.sparta.schedule.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.schedule.common.dto.CommonDto;
import org.sparta.schedule.entity.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UserResDto {
    private Long id;
    private String username;
    private String nickname;
    private String role;
    private LocalDateTime createAt;

    public UserResDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.role = user.getRole().name();
        this.createAt = user.getCreateAt();
    }
}
