package org.sparta.schedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.schedule.dto.ScheduleAddDto;
import org.sparta.schedule.dto.ScheduleDeleteDto;
import org.sparta.schedule.dto.ScheduleResDto;
import org.sparta.schedule.dto.ScheduleUpdateDto;
import org.sparta.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/schedules")
@RequiredArgsConstructor
@Tag(name = "Schedule API", description = "Schedule API 입니다.")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Operation(summary = "모든 일정 출력", description = "모든 일정을 가져온다.")
    @GetMapping
    public List<ScheduleResDto> getSchedules() {
        return scheduleService.getSchedules();
    }

    @Operation(summary = "선택한 일정 출력", description = "선택한 일정을 가져온다.")
    @GetMapping("/{id}")
    public ScheduleResDto getSchedule(@PathVariable long id) {
        return scheduleService.getSchedule(id);
    }

    @Operation(summary = "일정 추가", description = "일정을 추가한다.")
    @PostMapping
    public ScheduleResDto createSchedule(@RequestBody @Valid ScheduleAddDto addDto){
        return scheduleService.createSchedule(addDto);
    }

    @Operation(summary = "선택한 일정 수정", description = "선택한 일정을 수정한다")
    @PutMapping("/{id}")
    public ScheduleResDto updateSchedule(@PathVariable long id, @RequestBody @Valid ScheduleUpdateDto updateDto){
        return scheduleService.updateSchedule(id, updateDto);
    }

    @Operation(summary = "선택한 일정 삭제", description = "선택한 일정을 삭제한다")
    @DeleteMapping("/{id}")
    public long deleteSchedule(@PathVariable long id, @RequestBody @Valid ScheduleDeleteDto deleteDto){
        return scheduleService.deleteSchedule(id, deleteDto);
    }
}
