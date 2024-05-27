package org.sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.schedule.dto.ScheduleUpdateDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schedule")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Schedule extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;
    private String title;
    private String content;
    private String name;
    private String password;

    @OneToMany(mappedBy = "schedule")
    private List<Comment> comments = new ArrayList<>();

    public void updateSchedule(ScheduleUpdateDto updateDto) {
        this.title = updateDto.getTitle();
        this.content = updateDto.getContent();
        this.name = updateDto.getName();
    }
}
