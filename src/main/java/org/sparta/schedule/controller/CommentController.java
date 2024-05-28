package org.sparta.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.schedule.dto.CommentDeleteDto;
import org.sparta.schedule.dto.CommentReqDto;
import org.sparta.schedule.dto.CommentResDto;
import org.sparta.schedule.dto.CommentUpdateDto;
import org.sparta.schedule.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Comment Api", description = "댓글 관련 API 입니다.")
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 추가", description = "댓글을 추가한다.")
    @PostMapping("{scheduleId}")
    public CommentResDto addComment(@PathVariable("scheduleId") Long scheduleId,
                                    @RequestBody @Valid CommentReqDto reqDto) {
        return commentService.addComment(scheduleId, reqDto);
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정한다.")
    @PutMapping("{commentId}")
    public CommentResDto updateComment(@PathVariable("commentId") Long commentId,
                              @RequestBody @Valid CommentUpdateDto reqDto) {
        return commentService.updateComment(commentId, reqDto);
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제한다.")
    @DeleteMapping("{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId,
                                        @RequestBody @Valid CommentDeleteDto reqDto) {
        commentService.deleteComment(commentId, reqDto);
        return ResponseEntity.ok("성공했습니다.");
    }
}
