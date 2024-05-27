package org.sparta.schedule.common.file.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UploadAllowFile {
    PNG("png", ""),
    JPG("jpg", "")
    ;

    private String extension;
    private String mimeType;
}
