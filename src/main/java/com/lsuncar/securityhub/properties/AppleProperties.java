package com.lsuncar.securityhub.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "apple")
public record AppleProperties (    String teamId,
                                   String keyId,
                                   String clientId,
                                   Resource privateKeyPath) {
}
