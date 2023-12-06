package com.example.astra;

import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.example.astra.connection.DataStaxProperties;


@SpringBootApplication
@EnableConfigurationProperties(DataStaxProperties.class)
public class AgricultureDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgricultureDbApplication.class, args);
	}

	@Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
//        logger.debug("Secure Connect Bundle Path: {}", bundle);
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }
}
