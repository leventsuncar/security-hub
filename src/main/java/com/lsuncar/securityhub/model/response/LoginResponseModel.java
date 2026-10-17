package com.lsuncar.securityhub.model.response;

import lombok.Data;

@Data
public class LoginResponseModel {
    private String token;
    private Long companyId;
    private Long groupId;


    public LoginResponseModel(String token) {
        this.token = token;
    }
}
