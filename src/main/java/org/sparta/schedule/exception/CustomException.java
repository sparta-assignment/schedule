package org.sparta.schedule.exception;

public class CustomException extends RuntimeException {
    private int status = 500;
    CustomException(String msg) {
        super(msg);
    }

    CustomException(int status, String msg) {
        super(msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}