package org.sparta.schedule.common.exception;

import org.springframework.http.HttpStatus;

public class DuplicateIdException extends CustomException {
    public DuplicateIdException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
