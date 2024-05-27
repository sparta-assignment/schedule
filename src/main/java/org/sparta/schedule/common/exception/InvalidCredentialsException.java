package org.sparta.schedule.common.exception;

public class InvalidCredentialsException extends CustomException {
    public InvalidCredentialsException(String msg) {
        super(401, msg);
    }
}
