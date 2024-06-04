package org.sparta.schedule.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.schedule.common.dto.CommonDto;
import org.sparta.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleResDto {
    @Schema(description = "일정 id")
    private Long id;
    @Schema(description = "제목")
    private String title;
    @Schema(description = "내용")
    private String content;
    @Schema(description = "작성일")
    private LocalDateTime createAt;

    public ScheduleResDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createAt = schedule.getCreateAt();
    }
}
