package org.sparta.schedule.common.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(String msg) {
        super(HttpStatus.OK.value(), msg);
    }
}
