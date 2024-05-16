package org.sparta.schedule.global.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class FileUploadReq {
    @Schema(description = "업로드 파일")
    private List<MultipartFile> files;
}
