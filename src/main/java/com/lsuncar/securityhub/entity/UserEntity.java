package com.lsuncar.securityhub.entity;

import com.lsuncar.securityhub.enumerated.AuthProvider;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table ( name = "users" )
public class UserEntity implements UserDetails
{
	@Id
	@GeneratedValue ( strategy = GenerationType.UUID )
	private UUID id;

	@Column ( unique = true )
	private String username;

	@Column ( unique = true, nullable = false )
	private String email;

	private String password; // null olabilir sosyal login için

	@Enumerated ( EnumType.STRING )
	private AuthProvider provider; // GOOGLE, APPLE, LOCAL

	private String providerId; // OAuth2 user id
	private String accessToken;
	private String refreshToken;

	private boolean enabled = true;
	private boolean locked = false;

	@ElementCollection ( fetch = FetchType.EAGER )
	private Set<String> roles = new HashSet<>();

	private Instant createdAt = Instant.now();
	private Instant updatedAt = Instant.now();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities ()
	{
		return List.of();
	}

	@Override
	public String getPassword ()
	{
		return "";
	}

	@Override
	public String getUsername ()
	{
		return "";
	}

}
