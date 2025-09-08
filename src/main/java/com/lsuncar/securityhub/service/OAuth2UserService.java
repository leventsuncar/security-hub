package com.lsuncar.securityhub.service;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public interface OAuth2UserService<O, O1 extends OAuth2AuthenticatedPrincipal> extends org.springframework.security.oauth2.client.userinfo.OAuth2UserService
{
}
