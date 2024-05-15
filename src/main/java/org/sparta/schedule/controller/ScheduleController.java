package org.sparta.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.dto.ScheduleAddDto;
import org.sparta.schedule.dto.ScheduleResDto;
import org.sparta.schedule.dto.ScheduleUpdateDto;
import org.sparta.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    public List<ScheduleResDto> getSchedules() {
        return scheduleService.getSchedules();
    }

    @GetMapping("/{id}")
    public ScheduleResDto getSchedule(@PathVariable long id) {
        return scheduleService.getSchedule(id);
    }

    @PostMapping
    public ScheduleResDto createSchedule(@RequestBody ScheduleAddDto addDto){
        return scheduleService.createSchedule(addDto);
    }

    @PutMapping
    public ScheduleResDto updateSchedule(ScheduleUpdateDto updateDto){
        return null;
    }

    @DeleteMapping
    public String deleteSchedule(String scheduleId){
        return null;
    }
}
