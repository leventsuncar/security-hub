package com.lsuncar.securityhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SecurityHubApplication
{

	public static void main ( String[] args )
	{
		SpringApplication.run( SecurityHubApplication.class, args );
	}

}
