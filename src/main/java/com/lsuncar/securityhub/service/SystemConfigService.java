package com.lsuncar.securityhub.service;

import com.lsuncar.securityhub.config.SystemConfig;
import com.lsuncar.securityhub.entity.SystemConfigEntity;
import com.lsuncar.securityhub.enumerated.SystemConfigKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SystemConfigService {

    public SystemConfigEntity findByKey(String key);

    List<SystemConfigEntity> getAll();

    Boolean systemConfigExistsByKey(String key);


    Map<SystemConfigKeys, String> loadConfigs();
}
