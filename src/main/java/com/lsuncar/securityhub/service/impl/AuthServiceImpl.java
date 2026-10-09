package com.lsuncar.securityhub.service.impl;

import com.lsuncar.securityhub.model.LoginResponseModel;
import com.lsuncar.securityhub.service.AuthService;
import com.lsuncar.securityhub.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtils;


    @Override
    public LoginResponseModel login(String username, String password) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( username, password )
        );


        UserDetails user = (UserDetails) authentication.getPrincipal();
        assert user != null;
        String token = jwtUtils.generateToken( new HashMap<>(), user );

        return new LoginResponseModel(token);
    }

}
