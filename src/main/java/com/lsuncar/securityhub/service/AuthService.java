package com.lsuncar.securityhub.service;

import com.lsuncar.securityhub.model.LoginResponseModel;

public interface AuthService {
    LoginResponseModel login (String username, String password  );
}
