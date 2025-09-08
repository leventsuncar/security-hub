package com.lsuncar.securityhub.service.impl;

import com.lsuncar.securityhub.entity.UserEntity;
import com.lsuncar.securityhub.enumerated.AuthProvider;
import com.lsuncar.securityhub.repository.UserRepository;
import com.lsuncar.securityhub.service.OAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User>
{

	private UserRepository userRepository;

	public OAuth2UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public OAuth2User loadUser ( OAuth2UserRequest userRequest ) throws OAuth2AuthenticationException
	{
		OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
		String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
		String providerId = oAuth2User.getName();
		String email = oAuth2User.getAttribute("email");

		// DB’de kullanıcıyı bul veya yarat
		UserEntity user = userRepository.findByProviderAndProviderId(AuthProvider.valueOf( provider ), providerId)
				.orElseGet(() -> {
					UserEntity u = new UserEntity();
					u.setProvider( AuthProvider.valueOf(provider));
					u.setProviderId(providerId);
					u.setEmail(email);
					u.setRoles( Set.of("ROLE_USER"));
					return userRepository.save(u);
				});

		return oAuth2User;
	}
}
