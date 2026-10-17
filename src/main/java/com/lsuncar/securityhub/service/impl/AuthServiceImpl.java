package com.lsuncar.securityhub.service.impl;

import com.lsuncar.securityhub.entity.UserEntity;
import com.lsuncar.securityhub.enumerated.AuthProvider;
import com.lsuncar.securityhub.exception.UserAlreadyExistException;
import com.lsuncar.securityhub.model.request.LoginModel;
import com.lsuncar.securityhub.model.request.RegisterRequestModel;
import com.lsuncar.securityhub.model.response.LoginResponseModel;
import com.lsuncar.securityhub.repository.UserRepository;
import com.lsuncar.securityhub.service.AuthService;
import com.lsuncar.securityhub.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public LoginResponseModel login(LoginModel loginModel) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginModel.username(), loginModel.password())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        assert user != null;
        String token = jwtUtils.generateToken(new HashMap<>(), user);

        return new LoginResponseModel(token);
    }

    @Override
    public LoginResponseModel register(RegisterRequestModel registerRequestModel) {

        if (logger.isDebugEnabled())
            logger.debug("Registering new user: {}", registerRequestModel.username());

        userRepository.findUserEntitiesByUsername(registerRequestModel.username()).ifPresent(user -> {
            throw new UserAlreadyExistException("User already exist", "USER_ALREADY_EXIST");
        });

        userRepository.findUserEntitiesByEmailAndProvider(registerRequestModel.email(), AuthProvider.EMAIL).ifPresent(user -> {
            throw new UserAlreadyExistException("User already exist", "USER_ALREADY_EXIST");
        });

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerRequestModel.username());
        userEntity.setPassword(passwordEncoder.encode(registerRequestModel.password()));
        userEntity.setCreatedAt(Instant.now());
        userEntity.setEmail(registerRequestModel.email());
        userEntity.setEnabled(true);
        userEntity.setProvider(AuthProvider.EMAIL);

        userRepository.save(userEntity);

        // TODO: Implement email verification with a user-configurable email template.

        if (logger.isDebugEnabled())
            logger.debug("User registered: {}", registerRequestModel.username());

        return new LoginResponseModel(jwtUtils.generateToken(new HashMap<>(), userEntity));
    }
}
