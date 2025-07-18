package com.ian.demo.mcp.remote.config;

import com.ian.demo.mcp.remote.service.ToolService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfig {

    @Bean
    public ToolCallbackProvider toolCallbackProvider(ToolService toolService) {
        return MethodToolCallbackProvider.builder().toolObjects(toolService).build();
    }

}
