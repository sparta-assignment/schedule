package org.sparta.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.schedule.common.response.ListResult;
import org.sparta.schedule.common.response.ResponseService;
import org.sparta.schedule.common.response.SingleResult;
import org.sparta.schedule.common.security.UserDetailsImpl;
import org.sparta.schedule.dto.schedule.ScheduleAddDto;
import org.sparta.schedule.dto.schedule.ScheduleReadResDto;
import org.sparta.schedule.dto.schedule.ScheduleResDto;
import org.sparta.schedule.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("api/schedules")
@RequiredArgsConstructor
@Tag(name = "Schedule API", description = "Schedule API 입니다.")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ResponseService responseService;

    @Operation(summary = "모든 일정 출력", description = "모든 일정을 가져온다.")
    @GetMapping
    public ResponseEntity<ListResult<ScheduleReadResDto>> getSchedules() {
        return responseService.getListResult(
                scheduleService.getSchedules()
        );
    }

    @Operation(summary = "선택한 일정 출력", description = "선택한 일정을 가져온다.")
    @GetMapping("/{scheduleId}")
    public ResponseEntity<SingleResult<ScheduleReadResDto>> getSchedule(@PathVariable Long scheduleId) {
        return responseService.getSingleResult(
                scheduleService.getSchedule(scheduleId)
        );
    }

    @Operation(summary = "일정 추가", description = "일정을 추가한다.")
    @PostMapping
    public ResponseEntity<SingleResult<ScheduleResDto>> createSchedule(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @RequestBody @Valid ScheduleAddDto addDto){
        return responseService.getSingleResult(
                scheduleService.createSchedule(userDetails.getUser().getId(), addDto)
        );
    }

    @Operation(summary = "선택한 일정 수정", description = "선택한 일정을 수정한다")
    @PutMapping("/{scheduleId}")
    public ResponseEntity<SingleResult<ScheduleResDto>> updateSchedule(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @PathVariable Long scheduleId,
                                         @RequestBody @Valid ScheduleAddDto updateDto){
        return responseService.getSingleResult(
                scheduleService.updateSchedule(scheduleId, userDetails.getUser().getId(), updateDto)
        );
    }

    @Operation(summary = "선택한 일정 삭제", description = "선택한 일정을 삭제한다")
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<SingleResult<String>> deleteSchedule(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @PathVariable Long scheduleId){
        scheduleService.deleteSchedule(scheduleId, userDetails.getUser().getId());
        return responseService.getSingleResult("해당 일정 삭제를 완료하였습니다.");
    }
}
