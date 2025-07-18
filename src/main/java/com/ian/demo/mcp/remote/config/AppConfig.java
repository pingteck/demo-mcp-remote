package com.ian.demo.mcp.remote.config;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Log4j2
@ToString
@Getter
@Setter
@Configuration
@ConfigurationProperties("app")
public class AppConfig {

    private String host;
    private String basePath;
    private List<String> allowedOrigins;
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwksUri;

    @PostConstruct
    public void init() {
        log.info(this);
    }

}
