package com.lsuncar.securityhub.controller;

import com.lsuncar.securityhub.model.LoginModel;
import com.lsuncar.securityhub.model.LoginResponseModel;
import com.lsuncar.securityhub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ( value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
public class AuthController
{

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login ( @RequestBody LoginModel loginModel ) {

		LoginResponseModel loginResponseModel = authService.login(loginModel.username(), loginModel.password());



		return ResponseEntity.ok().body("Login successful");
	}


}
