package com.lsuncar.securityhub.service.impl;

import com.lsuncar.securityhub.entity.SystemConfigEntity;
import com.lsuncar.securityhub.enumerated.SystemConfigKeys;
import com.lsuncar.securityhub.exception.SystemConfigNotFoundException;
import com.lsuncar.securityhub.repository.SystemConfigRepository;
import com.lsuncar.securityhub.service.SystemConfigService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

    private static final Logger logger = LoggerFactory.getLogger(SystemConfigServiceImpl.class);

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Override
    public SystemConfigEntity findByKey(String key) {

        SystemConfigEntity systemConfigEntity = systemConfigRepository.findByKey(SystemConfigKeys.valueOf(key));

        if (isNull(systemConfigEntity)) {
            logger.warn("System config not found for key: {}", key);
            throw new SystemConfigNotFoundException("System config not found: " + key, "SYSTEM_CONFIG_NOT_FOUND");
        }

        return systemConfigEntity;
    }

    @Override
    @Transactional
    public List<SystemConfigEntity> getAll() {

        List<SystemConfigEntity> configs = systemConfigRepository.findAll();

        Set<String> enumKeys = Arrays.stream(SystemConfigKeys.values()).map(Enum::name).collect(Collectors.toSet());

        Set<String> dbKeys = configs.stream().map(SystemConfigEntity::getKey).collect(Collectors.toSet());

        List<SystemConfigEntity> missingConfigs = new ArrayList<>();

        for (SystemConfigKeys key : SystemConfigKeys.values()) {
            if (!dbKeys.contains(key.name())) {
                SystemConfigEntity entity = new SystemConfigEntity();
                entity.setKey(key.name());
                entity.setValue("");
                missingConfigs.add(entity);
            }
        }

        if (!missingConfigs.isEmpty()) {
            if (logger.isDebugEnabled()) logger.debug("Creating {} missing system configs", missingConfigs.size());

            configs.addAll(systemConfigRepository.saveAll(missingConfigs));
        }

        List<SystemConfigEntity> obsoleteConfigs = configs.stream().filter(entity -> !enumKeys.contains(entity.getKey())).toList();

        if (!obsoleteConfigs.isEmpty()) {
            if (logger.isDebugEnabled()) logger.debug("Removing {} obsolete system configs", obsoleteConfigs.size());

            systemConfigRepository.deleteAll(obsoleteConfigs);
            configs.removeAll(obsoleteConfigs);
        }

        return configs;
    }

    @Override
    public Boolean systemConfigExistsByKey(String key) {
        return null;
    }

    @Override
    public Map<SystemConfigKeys, String> loadConfigs() {

        List<SystemConfigEntity> systemConfigEntities = getAll();

        Map<SystemConfigKeys, String> cache = new HashMap<>();

        for (SystemConfigEntity systemConfigEntity : systemConfigEntities) {
            SystemConfigKeys key = SystemConfigKeys.valueOf(systemConfigEntity.getKey());

            if (key.isSecret()) {
                //todo decrypt
                logger.debug("System config value for key {} is masked", systemConfigEntity.getKey());

                //cache.put(key, "********");
            } else cache.put(key, systemConfigEntity.getValue());
        }
        return cache;
    }
}
