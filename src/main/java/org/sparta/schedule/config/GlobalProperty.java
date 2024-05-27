package org.sparta.schedule.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

public class GlobalProperty {
    @Configuration
    public static class File {
        @Value("${file.upload.path}")
        private String uploadPath;

        public String getUploadPath() {
            return uploadPath;
        }
    }
}
