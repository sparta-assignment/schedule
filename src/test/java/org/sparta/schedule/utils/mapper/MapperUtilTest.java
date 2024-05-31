package org.sparta.schedule.utils.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sparta.schedule.common.utils.mapper.MapperUtil;
import org.sparta.schedule.dto.ScheduleAddDto;
import org.sparta.schedule.entity.Schedule;


class MapperUtilTest {

    @Test
    @DisplayName("Mapper Util이 제대로 작동하는지 확인")
    public void test1() {
        // given
        ScheduleAddDto addDto = ScheduleAddDto.builder()
                .title("일정 제목")
                .content("일정 내용")
                .build();

        // when
        Schedule schedule1 = MapperUtil.toEntity(addDto, Schedule.class);
        Schedule schedule2 = MapperUtil.toEntity(addDto, Schedule.class, 1L);
//        Schedule schedule3 = new Schedule(1L, "asd", "asdf", "asdf", "asdf");

        System.out.println(schedule1);
        System.out.println(schedule2);
//        System.out.println(schedule3);
    }
}