package org.sparta.schedule.global.file.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.sparta.schedule.entity.TimeStamped;

@Entity
@Table(name = "file")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File extends TimeStamped {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "ori_file_name")
    private String oriFileName;
    @Column(name = "save_path")
    private String saveFilePath;
    @Column(name = "extension")
    private String extension;
}
