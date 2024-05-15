package org.sparta.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleUpdateDto {
    @Schema(description = "제목")
    @NotNull
    @Size(max = 200)
    private String title;
    @Schema(description = "내용")
    private String content;
    @Schema(description = "담당자")
    @Pattern(regexp = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$", message = "이메일 형태로 입력되어져야 합니다.")
    private String name;
    @Schema(description = "비밀번호")
    @NotNull
    private String password;
}
