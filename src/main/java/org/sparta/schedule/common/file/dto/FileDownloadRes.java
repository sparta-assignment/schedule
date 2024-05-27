package org.sparta.schedule.common.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;


@Getter
@AllArgsConstructor
public class FileDownloadRes {
    HttpHeaders headers;
    Resource resource;
}
