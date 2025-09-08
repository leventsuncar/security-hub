package com.lsuncar.securityhub.service.impl;

import com.lsuncar.securityhub.repository.UserRepository;
import com.lsuncar.securityhub.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException
	{
		return null;
	}
}
