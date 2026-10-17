package com.lsuncar.securityhub.cache;

import com.lsuncar.securityhub.enumerated.SystemConfigKeys;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SystemConfigCache {

    private volatile Map<SystemConfigKeys, String> configs = Map.of();

    public String get(SystemConfigKeys key) {
        return configs.get(key);
    }

    public void replace(Map<SystemConfigKeys, String> newConfigs) {
        configs = Map.copyOf(newConfigs);
    }

}
