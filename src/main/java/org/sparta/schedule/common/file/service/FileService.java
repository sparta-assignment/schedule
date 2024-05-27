package org.sparta.schedule.common.file.service;

import org.sparta.schedule.common.exception.CustomException;
import org.sparta.schedule.common.file.dto.FileDownloadDto;
import org.sparta.schedule.common.file.dto.FileDownloadRes;
import org.sparta.schedule.common.file.dto.FileUploadRes;
import org.sparta.schedule.common.file.repository.FileRepository;
import org.sparta.schedule.common.file.entity.File;
import org.sparta.schedule.config.GlobalProperty;
import org.sparta.schedule.common.file.dto.FileUploadReq;
import org.sparta.schedule.common.utils.FileUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final String basicPath;
    private final String separator = java.io.File.separator;

    public FileService(GlobalProperty.File fileProperty,
                       FileRepository fileRepository) {
        this.basicPath = fileProperty.getUploadPath();
        this.fileRepository = fileRepository;
    }

    public List<FileUploadRes> uploadFile(FileUploadReq req) {
        List<FileUploadRes> res = new ArrayList<>();

        for (MultipartFile file : req.getFiles()) {
            // 디스크에 파일 저장
            String extension =  "." + FileUtil.getFileExtension(file.getOriginalFilename());
            String fullPath = getFullSaveFilePath(getSaveFileName()) + extension;
            if (!FileUtil.save(file, fullPath)) {
                // 저장에 실패하면 아무런 값을 넣지 않음
                res.add(new FileUploadRes());
                continue;
            }
            // 완료되면 데이터베이스에 추가
            File saveFile = saveFile(fullPath, file.getOriginalFilename());

            res.add(FileUploadRes.builder()
                    .id(saveFile.getId())
                    .savePath(saveFile.getSaveFilePath())
                    .build()
            );
        }
        return res;
    }

    public FileDownloadRes downloadFile(String id) {
        File file = fileRepository.findById(id).orElseThrow(
                () -> new CustomException(200, "해당하는 파일을 찾을 수 없습니다.")
        );
        // 파일 경로 가져오기
        FileDownloadDto downloadDto = FileDownloadDto.builder()
                .fileName(file.getOriFileName())
                .filePath(file.getSaveFilePath())
                .extension(file.getExtension())
                .build();

        return getFileResource(downloadDto);
    }

    public FileDownloadRes getFileResource(FileDownloadDto downloadDto) {
        HttpHeaders headers = new HttpHeaders();
        String oriFileName = downloadDto.getFileName();
        // 업로드 파일명이 없으면 랜덤으로 생성
        if ("".equals(downloadDto.getFileName())){
            oriFileName = UUID.randomUUID() + "." + downloadDto.getExtension();
        }

        // 파일 다운로드 헤더 작성
        String encodedOriginalFileName = UriUtils.encode(oriFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";
        headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        Resource resource = FileUtil.download(downloadDto.getFilePath());

        return new FileDownloadRes(headers, resource);
    }

    /**
     * 데이터베이스에 파일 정보를 저장한다.
     * @param saveFilePath basicPath를 제외한 파일 저장 경로
     * @param oriFileName 원래 파일명
     * @return 저장 완료한 파일 정보
     */
    private File saveFile(String saveFilePath, String oriFileName) {
        return fileRepository.save(
                File.builder()
                        .saveFilePath(saveFilePath)
                        .oriFileName(oriFileName)
                        .build()
        );
    }

    /**
     * 디스크에 파일 업로드시 가져올 파일 경로 계산
     * @param filePath basicPath를 제외한 파일 저장 경로
     * @return basicPath를 포함한 전체 파일 경로
     */
    private String getFullSaveFilePath(String filePath) {
        return basicPath + (hasFirstSeparator(filePath)? filePath : separator + filePath);
    }

    /**
     * 파일 경로에 맨 앞에 구분자가 있는지 확인
     * @param path 확인할 파일 경로 문자열
     * @return 파일 경로에 맨 앞 구분자 유무 반환
     */
    private boolean hasFirstSeparator(String path){
        return path.charAt(0) == this.separator.charAt(0);
    }

    private String getSaveFileName(){
        return UUID.randomUUID().toString().substring(1,8) + System.currentTimeMillis();
    }
}
