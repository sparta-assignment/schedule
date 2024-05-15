package org.sparta.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.dto.ScheduleResDto;
import org.sparta.schedule.dto.ScheduleUpdateDto;
import org.sparta.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    @GetMapping
    public List<ScheduleResDto> getSchedules() {
        return null;
    }

    @PostMapping
    public ScheduleResDto createSchedule(){
        return null;
    }

    @PutMapping
    public ScheduleResDto updateSchedule(ScheduleUpdateDto scheduleDto){
        return null;
    }

    @DeleteMapping
    public String deleteSchedule(String scheduleId){
        return null;
    }
}
