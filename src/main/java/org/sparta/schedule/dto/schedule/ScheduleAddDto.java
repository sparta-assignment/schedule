package org.sparta.schedule.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.sparta.schedule.common.dto.CommonDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleAddDto implements CommonDto {
    @Schema(description = "제목")
    @NotNull
    @Size(max = 200)
    private String title;
    @Schema(description = "내용")
    private String content;
}
