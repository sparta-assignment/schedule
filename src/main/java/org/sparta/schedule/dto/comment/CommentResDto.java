package org.sparta.schedule.dto.comment;

import lombok.Getter;
import org.sparta.schedule.dto.UserResDto;
import org.sparta.schedule.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResDto {
    private Long comment_id;
    private String content;
    private String username;
    private Long schedule_id;
    private LocalDateTime create_date;

    public CommentResDto(Comment comment) {
        this.comment_id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.schedule_id = comment.getSchedule().getId();
        this.create_date = comment.getCreateAt();
    }
}
