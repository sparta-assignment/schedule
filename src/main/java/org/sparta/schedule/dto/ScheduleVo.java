package org.sparta.schedule.dto;

import org.sparta.schedule.entity.Comment;
import org.sparta.schedule.entity.User;

import java.util.List;

public record ScheduleVo(
        Long id,
        String title,
        String content,
        User user,
        List<Comment> comments
) {
}
