package org.sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.dto.schedule.ScheduleAddDto;
import org.sparta.schedule.dto.schedule.ScheduleReadResDto;
import org.sparta.schedule.dto.schedule.ScheduleResDto;
import org.sparta.schedule.dto.schedule.ScheduleVo;
import org.sparta.schedule.entity.Schedule;
import org.sparta.schedule.common.exception.DataNotFoundException;
import org.sparta.schedule.common.exception.InvalidCredentialsException;
import org.sparta.schedule.entity.User;
import org.sparta.schedule.repository.ScheduleRepository;
import org.sparta.schedule.common.utils.mapper.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleResDto createSchedule(Long userId, ScheduleAddDto addDto) {
        ScheduleVo scheduleVo = new ScheduleVo(
                null,
                addDto.getTitle(),
                addDto.getContent(),
                getUser(userId),
                null
        );
        Schedule schedule = MapperUtil.toEntity(scheduleVo, Schedule.class);
        return new ScheduleResDto(
                scheduleRepository.save(schedule)
        );
    }

    public ScheduleReadResDto getSchedule(Long scheduleId) {
        return new ScheduleReadResDto(
                findById(scheduleId)
        );
    }

    public List<ScheduleReadResDto> getSchedules() {
        return scheduleRepository.findAllByOrderByCreateAtDesc()
                .stream().map(ScheduleReadResDto::new)
                .toList();
    }

    @Transactional
    public ScheduleResDto updateSchedule(Long scheduleId, Long userId, ScheduleAddDto updateDto) {
        Schedule schedule = findById(scheduleId);
        checkUserId(userId, schedule.getUser().getId());
        schedule.updateSchedule(updateDto);
        return new ScheduleResDto(schedule);
    }

    public long deleteSchedule(Long scheduleId, Long userId) {
        Schedule schedule = findById(scheduleId);
        checkUserId(userId, schedule.getUser().getId());
        scheduleRepository.delete(schedule);
        return scheduleId;
    }

    private void checkUserId(Long inputUserId, Long userId) {
        if (!Objects.equals(inputUserId, userId)) {
            throw new InvalidCredentialsException("작성자가 일치하지 않습니다.");
        }
    }

    private User getUser(Long userId) {
        return User.builder()
                .id(userId)
                .build();
    }

    public Schedule findById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new DataNotFoundException("해당하는 일정이 없습니다."));
    }
}
