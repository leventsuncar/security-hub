package com.lsuncar.securityhub.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google")
public record GoogleProperties( String clientId, String clientSecret ) {
}
