package com.lsuncar.securityhub.entity;

import com.lsuncar.securityhub.enumerated.AuthProvider;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    //crypted
    private String password; // null olabilir oauth için

	@Enumerated ( EnumType.STRING )
	private AuthProvider provider; // GOOGLE, APPLE, EMAIL

	private String providerId; // OAuth2 user id
//	private String accessToken;
//	private String refreshToken;

    private boolean enabled = true;
    private boolean locked = false;

//	@ElementCollection ( fetch = FetchType.EAGER )
//	private Set<String> roles = new HashSet<>();

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
