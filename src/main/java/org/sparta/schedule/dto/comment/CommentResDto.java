package org.sparta.schedule.dto.comment;

import lombok.Getter;
import org.sparta.schedule.common.dto.CommonDto;
import org.sparta.schedule.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResDto implements CommonDto {
    private Long commentId;
    private String content;
    private String username;
    private Long scheduleId;
    private LocalDateTime createAt;

    public CommentResDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.scheduleId = comment.getSchedule().getId();
        this.createAt = comment.getCreateAt();
    }
}
