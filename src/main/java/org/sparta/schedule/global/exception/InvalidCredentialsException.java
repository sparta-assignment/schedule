package org.sparta.schedule.global.exception;

public class InvalidCredentialsException extends CustomException {
    public InvalidCredentialsException(String msg) {
        super(401, msg);
    }
}
