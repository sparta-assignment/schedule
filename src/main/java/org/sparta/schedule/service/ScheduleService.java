package org.sparta.schedule.service;

import lombok.RequiredArgsConstructor;
import org.sparta.schedule.dto.ScheduleAddDto;
import org.sparta.schedule.dto.ScheduleDeleteDto;
import org.sparta.schedule.dto.ScheduleResDto;
import org.sparta.schedule.dto.ScheduleUpdateDto;
import org.sparta.schedule.entity.Schedule;
import org.sparta.schedule.exception.DataNotFoundException;
import org.sparta.schedule.exception.InvalidCredentialsException;
import org.sparta.schedule.repository.ScheduleRepository;
import org.sparta.schedule.utils.mapper.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleResDto createSchedule(ScheduleAddDto addDto) {
        Schedule schedule = MapperUtil.toEntity(addDto, Schedule.class);
        return new ScheduleResDto(
                scheduleRepository.save(schedule)
        );
    }

    public ScheduleResDto getSchedule(long id) {
        return new ScheduleResDto(
                findById(id)
        );
    }

    public List<ScheduleResDto> getSchedules() {
        return scheduleRepository.findAllByOrderByCreateAtDesc()
                .stream().map(ScheduleResDto::new)
                .toList();
    }

    @Transactional
    public ScheduleResDto updateSchedule(Long id, ScheduleUpdateDto updateDto) {
        Schedule schedule = findById(id);
        checkPassword(schedule.getPassword(), updateDto.getPassword());
        schedule.updateSchedule(updateDto);
        return new ScheduleResDto(schedule);
    }

    public long deleteSchedule(long id, ScheduleDeleteDto deleteDto) {
        Schedule schedule = findById(id);
        checkPassword(schedule.getPassword(), deleteDto.getPassword());
        scheduleRepository.delete(schedule);
        return id;
    }

    private void checkPassword(String password, String targetPassword) {
        if (!Objects.equals(password, targetPassword)) {
            throw new InvalidCredentialsException("비밀번호가 일치하지 않습니다.");
        }
    }

    private Schedule findById(long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("해당하는 일정이 없습니다."));
    }
}
