package org.sparta.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleResDto {
    private Long id;
    private String title;
    private String content;
    private String name;
    private LocalDateTime createAt;

    public ScheduleResDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.name = schedule.getName();
        this.createAt = schedule.getCreateAt();
    }
}
