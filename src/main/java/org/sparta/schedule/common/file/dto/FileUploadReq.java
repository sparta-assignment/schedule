package org.sparta.schedule.common.file.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sparta.schedule.common.file.validation.FileUploadValid;
import org.sparta.schedule.common.file.validation.UploadAllowFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class FileUploadReq {
    @Schema(description = "업로드 파일")
    @FileUploadValid(allowExtension = {UploadAllowFile.JPG, UploadAllowFile.PNG})
    private List<MultipartFile> files;
}
