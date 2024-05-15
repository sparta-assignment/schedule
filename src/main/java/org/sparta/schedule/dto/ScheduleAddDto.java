package org.sparta.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.schedule.utils.mapper.MapperField;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleAddDto {
    private String title;
    private String content;
    private String name;
    private String password;
    private LocalDateTime createAt;
}
