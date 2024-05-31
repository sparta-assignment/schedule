package org.sparta.schedule.dto.comment;

import org.sparta.schedule.entity.Schedule;
import org.sparta.schedule.entity.User;

public record CommentVo(
        Long id,
        String content,
        User user,
        Schedule schedule
) {}