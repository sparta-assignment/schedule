package org.sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.common.exception.DataNotFoundException;
import org.sparta.schedule.common.exception.InvalidCredentialsException;
import org.sparta.schedule.common.utils.mapper.MapperUtil;
import org.sparta.schedule.dto.CommentReqDto;
import org.sparta.schedule.dto.CommentResDto;
import org.sparta.schedule.dto.CommentUpdateDto;
import org.sparta.schedule.entity.Comment;
import org.sparta.schedule.entity.Schedule;
import org.sparta.schedule.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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

    @Transactional
    public CommentResDto updateComment(Long commentId, CommentUpdateDto reqDto) {
        Comment comment = findById(commentId);
        checkWriter(reqDto.getWriter(), comment.getWriter());
        comment.updateComment(reqDto);
        return new CommentResDto(comment);
    }

    private void checkWriter(String inputWriter, String writer) {
        if (!Objects.equals(inputWriter, writer)) {
            throw new InvalidCredentialsException("작성자가 일치하지 않습니다.");
        }
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new DataNotFoundException("해당하는 댓글 데이터를 찾을 수 없습니다.")
        );
    }
}
