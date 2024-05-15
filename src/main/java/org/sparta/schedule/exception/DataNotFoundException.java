package org.sparta.schedule.exception;

public class DataNotFoundException extends CustomException{
    public DataNotFoundException(String msg) {
        super(404, msg);
    }
}
