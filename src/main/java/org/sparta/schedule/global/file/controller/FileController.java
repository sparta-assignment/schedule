package org.sparta.schedule.global.file.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sparta.schedule.global.file.dto.FileDownloadRes;
import org.sparta.schedule.global.file.dto.FileUploadRes;
import org.sparta.schedule.global.file.dto.FileUploadReq;
import org.sparta.schedule.global.file.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "File api", description = "File api 입니다.")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileUploadRes> uploadFile(@ModelAttribute @Valid FileUploadReq req){
        return fileService.uploadFile(req);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id){
        FileDownloadRes downloadRes = fileService.downloadFile(id);
        return new ResponseEntity<>(downloadRes.getResource(), downloadRes.getHeaders(), HttpStatus.OK);
    }
}
