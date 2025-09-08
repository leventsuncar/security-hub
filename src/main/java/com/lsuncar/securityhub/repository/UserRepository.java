package com.lsuncar.securityhub.repository;

import com.lsuncar.securityhub.entity.UserEntity;
import com.lsuncar.securityhub.enumerated.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>
{
	UserEntity findByUsername ( String username );

	Optional<UserEntity> findByProviderAndProviderId ( AuthProvider provider, String providerId );
}
