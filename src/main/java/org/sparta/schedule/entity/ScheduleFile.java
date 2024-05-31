package org.sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schedule_file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ScheduleFile extends TimeStamped{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String extension;
    private int size;
    @Lob
    @Column(name = "content", columnDefinition = "BLOB")
    private byte[] content;
}
