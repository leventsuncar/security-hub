package com.lsuncar.securityhub.exception;

public class SystemConfigNotFoundException extends RuntimeException {

    private String code;

    public SystemConfigNotFoundException(String message) {
        super(message);
    }

    public SystemConfigNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }

}
