package org.sparta.schedule.common.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FileDownloadDto {
    private String fileName = "";
    private String filePath;
    private String extension;
}
