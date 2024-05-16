package org.sparta.schedule.utils;

import org.sparta.schedule.global.exception.CustomException;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class FileUtil {
    /**
     * 파일 저장
     * @param saveFile 저장할 파일
     * @param fullPath 저장할 파일 경로
     * @return 파일 저장 성공 여부
     */
    public static boolean save(MultipartFile saveFile, String fullPath) {
        File folder = null;
        try {
            folder = new File(fullPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // 파일 객체 생성 후 파일 저장
            File dst = new File(fullPath);
            // 실제 파일 저장
            saveFile.transferTo(dst);
            return true;
        } catch (IOException e) {
            folder.delete();
        }
        return false;
    }

    public static UrlResource download(String fullPath) {
        try {
            return new UrlResource("file:"  + fullPath);
        }catch (MalformedURLException e) {
            throw new CustomException(200, "오류가 발생하여 파일을 다운로드에 실패했습니다.");
        }
    }
}
