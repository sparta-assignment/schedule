package org.sparta.schedule.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.schedule.dto.comment.CommentResDto;
import org.sparta.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ScheduleReadResDto {
    @Schema(description = "일정 id")
    private Long id;
    @Schema(description = "제목")
    private String title;
    @Schema(description = "내용")
    private String content;
    @Schema(description = "작성일")
    private LocalDateTime createAt;
    @Schema(description = "댓글")
    private List<CommentResDto> comments;

    public ScheduleReadResDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createAt = schedule.getCreateAt();
        this.comments = schedule.getComments().stream().map(CommentResDto::new).toList();
    }
}
