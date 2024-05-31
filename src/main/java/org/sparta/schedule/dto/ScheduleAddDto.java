package org.sparta.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleAddDto {
    @Schema(description = "제목")
    @NotNull
    @Size(max = 200)
    private String title;
    @Schema(description = "내용")
    private String content;
}
