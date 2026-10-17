package com.lsuncar.securityhub.scheduled;

import com.lsuncar.securityhub.cache.SystemConfigCache;
import com.lsuncar.securityhub.service.SystemConfigService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ConfigJobs {

    @Autowired
    private SystemConfigCache configCache;

    @Autowired
    private SystemConfigService systemConfigService;

    @PostConstruct
    public void initialize() {
        reloadSystemConfigs();
    }

    //second, minute, hours, days, months, days off week
    //every five minutes

    @Scheduled(cron = "0 */5 * * * *")
    public void reloadSystemConfigs() {
        configCache.replace(systemConfigService.loadConfigs());
    }


}
