package com.lsuncar.securityhub.entity;

import com.lsuncar.securityhub.enumerated.AuthProvider;
import com.lsuncar.securityhub.model.SecureUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"provider_id","provider"}, name = "provider_provider_id"))
public class UserEntity extends SecureUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column ( name = "password" )
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider; // GOOGLE, APPLE, EMAIL

    private String providerId; // OAuth2 user id
//	private String accessToken;
//	private String refreshToken;

    private boolean enabled = true;
    private boolean locked = false;

    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }



//	@ElementCollection ( fetch = FetchType.EAGER )
//	private Set<String> roles = new HashSet<>();




}
