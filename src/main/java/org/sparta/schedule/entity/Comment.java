package org.sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.schedule.dto.CommentUpdateDto;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Table(name = "comment")
public class Comment extends TimeStamped{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private String writer;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public void addSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void updateComment(CommentUpdateDto updateDto) {
        this.content = updateDto.getContent();
    }
}
