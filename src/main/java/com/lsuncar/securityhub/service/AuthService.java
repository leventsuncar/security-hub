package com.lsuncar.securityhub.service;

import com.lsuncar.securityhub.model.request.LoginModel;
import com.lsuncar.securityhub.model.request.RegisterRequestModel;
import com.lsuncar.securityhub.model.response.LoginResponseModel;

public interface AuthService {
    LoginResponseModel login (LoginModel loginModel);

    LoginResponseModel register (RegisterRequestModel registerRequestModel);
}
