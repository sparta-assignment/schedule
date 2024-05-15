package org.sparta.schedule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    private Long id;
}
