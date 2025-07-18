package com.ian.demo.mcp.remote.config;

import com.ian.demo.mcp.remote.context.interceptor.ContextInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AppConfig appConfig;
    private final ContextInterceptor contextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(contextInterceptor)
            .addPathPatterns(appConfig.getBasePath() + "/mcp/message");
    }

}
