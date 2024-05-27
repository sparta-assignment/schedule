package org.sparta.schedule.common.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadRes {
    @Schema(description = "파일 아이디")
    private String id;
    @Schema(description = "저장 파일 경로")
    private String savePath;
}