package org.sparta.schedule.common.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends CustomException{
    public DataNotFoundException(String msg) {
        super(HttpStatus.OK.value(), msg);
    }
}
