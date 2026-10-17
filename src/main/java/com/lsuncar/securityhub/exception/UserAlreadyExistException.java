package com.lsuncar.securityhub.exception;

public class UserAlreadyExistException extends RuntimeException {

    private String code;

    public String getCode() {
        return code;
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException(String message, String code) {
        super(message);
        this.code = code;
    }
}
