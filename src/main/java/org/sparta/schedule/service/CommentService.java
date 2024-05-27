package org.sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.common.utils.mapper.MapperUtil;
import org.sparta.schedule.dto.CommentReqDto;
import org.sparta.schedule.dto.CommentResDto;
import org.sparta.schedule.entity.Comment;
import org.sparta.schedule.entity.Schedule;
import org.sparta.schedule.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ScheduleService scheduleService;
    private final CommentRepository commentRepository;


    public CommentResDto addComment(Long scheduleId, CommentReqDto reqDto) {
        Schedule schedule = scheduleService.findById(scheduleId);
        Comment comment = MapperUtil.toEntity(reqDto, Comment.class);

        comment.addSchedule(schedule);
        return new CommentResDto(commentRepository.save(comment));
    }
}
