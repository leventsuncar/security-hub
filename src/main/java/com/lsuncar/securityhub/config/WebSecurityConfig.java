package com.lsuncar.securityhub.config;

import com.lsuncar.securityhub.filter.AuthenticationFilter;
import com.lsuncar.securityhub.service.OAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig
{
	private final AuthenticationFilter authenticationFilter;
	private final UserDetailsService userDetailsService;
	private final OAuth2UserService oAuth2UserService;

	public WebSecurityConfig ( AuthenticationFilter authenticationFilter, UserDetailsService userDetailsService,
			OAuth2UserService oAuth2UserService )
	{
		this.authenticationFilter = authenticationFilter;
		this.userDetailsService = userDetailsService;
		this.oAuth2UserService = oAuth2UserService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain ( HttpSecurity http ) throws Exception
	{
		http
				.csrf( csrf -> csrf.disable() )
				.cors( cors -> cors.disable() )
				.authorizeHttpRequests( auth ->
						auth
								.requestMatchers( "/auth/**", "/oauth2/**" )
								.permitAll()
								.anyRequest().authenticated() )
				.oauth2Login( oauth2 ->
						oauth2
								.loginPage( "/auth/login" ).defaultSuccessUrl( "/auth/success" )
								.userInfoEndpoint( userInfo -> userInfo.userService( oAuth2UserService ) ) )
				.sessionManagement( session ->
						session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) )
				.addFilterBefore( authenticationFilter, UsernamePasswordAuthenticationFilter.class );

		return http.build();
	}

}
