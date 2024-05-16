package org.sparta.schedule.global.file.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolationException;
import org.sparta.schedule.utils.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

public class FileUploadValidator implements ConstraintValidator<FileUploadValid, List<MultipartFile>> {

    private FileUploadValid constraintAnnotation;

    @Override
    public void initialize(FileUploadValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext constraintValidatorContext) {
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            String extension = FileUtil.getFileExtension(fileName);
            UploadAllowFile[] allowFiles = constraintAnnotation.allowExtension();
            if (!isAllowedExtension(allowFiles, extension)) {
                String message = constraintAnnotation.message();
                if ("".equals(constraintAnnotation.message())) {
                    message = "[" + fileName + "] 파일은 허용되지 않는 확장자입니다.";
                }
                constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
                return false;
            }
        }
        return true;
    }

    private boolean isAllowedExtension(UploadAllowFile[] allowFiles, String extension) {
     for (UploadAllowFile allowFile: allowFiles) {
         if (Objects.equals(allowFile.getExtension(), extension)) {
             return true;
         }
     }
     return false;
    }
}
