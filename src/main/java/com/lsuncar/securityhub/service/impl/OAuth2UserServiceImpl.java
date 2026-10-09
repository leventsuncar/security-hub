package com.lsuncar.securityhub.service.impl;

import com.lsuncar.securityhub.entity.UserEntity;
import com.lsuncar.securityhub.enumerated.AuthProvider;
import com.lsuncar.securityhub.repository.UserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class OAuth2UserServiceImpl implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserRepository userRepository;
    private final OidcUserService oidcUserService;

    public OAuth2UserServiceImpl(UserRepository userRepository, OidcUserService oidcUserService) {
        this.userRepository = userRepository;
        this.oidcUserService = oidcUserService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oAuth2User = oidcUserService.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        String providerId = oAuth2User.getName();
        String email = oAuth2User.getAttribute("email");

        // DB’de kullanıcıyı bul veya yarat
        UserEntity user = userRepository.findByProviderAndProviderId(AuthProvider.valueOf(provider), providerId)
                .orElseGet(() -> {
                    UserEntity u = new UserEntity();
                    u.setProvider(AuthProvider.valueOf(provider));
                    u.setProviderId(providerId);
                    u.setEmail(email);
                    //u.setRoles( Set.of("ROLE_USER"));
                    return userRepository.save(u);
                });

        return oAuth2User;
    }

}
