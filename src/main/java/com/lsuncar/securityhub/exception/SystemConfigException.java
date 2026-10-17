package com.lsuncar.securityhub.exception;

public class SystemConfigException extends RuntimeException {

    private String code;

    public SystemConfigException(String message) {
        super(message);
    }

    public SystemConfigException(String message, String code) {
        super(message);
        this.code = code;
    }
}
