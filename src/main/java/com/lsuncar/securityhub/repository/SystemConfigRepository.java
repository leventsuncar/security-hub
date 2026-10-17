package com.lsuncar.securityhub.repository;

import com.lsuncar.securityhub.entity.SystemConfigEntity;
import com.lsuncar.securityhub.enumerated.SystemConfigKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SystemConfigRepository extends JpaRepository<SystemConfigEntity, SystemConfigKeys> {

    SystemConfigEntity findByKey(SystemConfigKeys key);

    @Query("SELECT s FROM SystemConfigEntity s")
    List<SystemConfigEntity> findAll();

    //Boolean systemConfigExistsByKey(SystemConfigKeys key);

}
