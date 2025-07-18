package com.ian.demo.mcp.remote.config;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Log4j2
@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final String protectedResourceUrl;
    private final List<String> allowedOrigins;

    public SecurityConfig(AppConfig appConfig) {
        this.protectedResourceUrl =
            appConfig.getHost() + "/.well-known/oauth-protected-resource" + appConfig.getBasePath();
        this.allowedOrigins = appConfig.getAllowedOrigins();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .authorizeHttpRequests(auth ->
                auth.requestMatchers("/actuators/**").permitAll()
                    .requestMatchers("/.well-known/**").permitAll()
                    .anyRequest().authenticated())
            .oauth2ResourceServer(resource ->
                resource.jwt(Customizer.withDefaults())
                    .authenticationEntryPoint((request, response, exception) -> {
                        String remoteAddr = request.getRemoteAddr();
                        String localAddr = request.getLocalAddr();
                        String requestUrl = request.getRequestURL().toString();
                        String requestURI = request.getRequestURI();
                        String token = request.getHeader("Authorization");
                        log.info(
                            "[authenticationEntryPoint] localAddr: {}, remoteAddr: {}, url: {}, uri: {}, token: {}",
                            localAddr, remoteAddr, requestUrl, requestURI, token);
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                        response.addHeader("WWW-Authenticate",
                            "Bearer resource_metadata=" + protectedResourceUrl);
                        log.error(
                            "[authenticationEntryPoint] exception: {}", exception.getMessage(),
                            exception);
                    })
                    .accessDeniedHandler((request, response, exception) -> {
                        log.error("[accessDeniedHandler] exception: {}", exception.getMessage(),
                            exception);
                    }))
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(getCorsConfiguration()))
            .build();
    }

    private CorsConfigurationSource getCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
