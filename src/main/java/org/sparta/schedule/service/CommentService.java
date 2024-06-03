package org.sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.common.exception.DataNotFoundException;
import org.sparta.schedule.common.exception.InvalidCredentialsException;
import org.sparta.schedule.common.utils.mapper.MapperUtil;
import org.sparta.schedule.dto.comment.*;
import org.sparta.schedule.entity.Comment;
import org.sparta.schedule.entity.Schedule;
import org.sparta.schedule.entity.User;
import org.sparta.schedule.repository.CommentRepository;
import org.sparta.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ScheduleService scheduleService;
    private final CommentRepository commentRepository;

    public CommentResDto addComment(Long userId, Long scheduleId, CommentReqDto reqDto) {
        Schedule schedule = scheduleService.findById(scheduleId);
        CommentVo commentVo = new CommentVo(
                null,
                reqDto.getContent(),
                getUser(userId),
                schedule
                );
        Comment comment = MapperUtil.toEntity(commentVo, Comment.class);

        return new CommentResDto(commentRepository.save(comment));
    }

    @Transactional
    public CommentResDto updateComment(Long userId, Long scheduleId, Long commentId, CommentReqDto reqDto) {
        Comment comment = findByIdAndSchedule_Id(commentId, scheduleId);
        checkWriter(userId, comment.getUser().getId());
        comment.updateComment(reqDto);
        return new CommentResDto(comment);
    }

    public void deleteComment(Long userId, Long scheduleId, Long commentId) {
        Comment comment = findByIdAndSchedule_Id(commentId, scheduleId);
        checkWriter(userId, comment.getUser().getId());
        commentRepository.delete(comment);
    }

    private void checkWriter(Long inputUserId, Long userId) {
        if (!Objects.equals(inputUserId, userId)) {
            throw new InvalidCredentialsException("작성자가 일치하지 않습니다.");
        }
    }

    private User getUser(Long userId) {
        return User.builder()
                .id(userId)
                .build();
    }

    private Comment findByIdAndSchedule_Id(Long commentId, Long scheduleId) {
        return commentRepository.findByIdAndSchedule_Id(commentId, scheduleId).orElseThrow(
                () -> new DataNotFoundException("해당하는 일정 데이터를 찾을 수 없거나 댓글 데이터를 찾을 수 없습니다.")
        );
    }
}
