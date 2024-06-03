package org.sparta.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.schedule.common.response.ResponseService;
import org.sparta.schedule.common.response.SingleResult;
import org.sparta.schedule.common.security.UserDetailsImpl;
import org.sparta.schedule.dto.comment.CommentReqDto;
import org.sparta.schedule.dto.comment.CommentResDto;
import org.sparta.schedule.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Comment API", description = "댓글 관련 API 입니다.")
@RequestMapping("/api/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ResponseService responseService;

    @Operation(summary = "댓글 추가", description = "댓글을 추가한다.")
    @PostMapping
    public ResponseEntity<SingleResult<CommentResDto>> addComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @PathVariable("scheduleId") Long scheduleId,
                                                                 @RequestBody @Valid CommentReqDto reqDto) {
        return responseService.getSingleResult(
                commentService.addComment(userDetails.getUser().getId(), scheduleId, reqDto)
        );
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정한다.")
    @PutMapping("/{commentId}")
    public ResponseEntity<SingleResult<CommentResDto>> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @PathVariable("scheduleId") Long scheduleId,
                                       @PathVariable("commentId") Long commentId,
                                       @RequestBody @Valid CommentReqDto reqDto) {
        return responseService.getSingleResult(
                commentService.updateComment(userDetails.getUser().getId(), commentId, commentId, reqDto)
        );
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제한다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<SingleResult<String>> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @PathVariable("scheduleId") Long scheduleId,
                                                @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(userDetails.getUser().getId(), scheduleId, commentId);
        return responseService.getSingleResult("성공했습니다.");
    }
}
