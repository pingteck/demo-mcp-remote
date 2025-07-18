package com.ian.demo.mcp.remote.config;

import com.ian.demo.mcp.remote.context.model.ContextSnapshot;
import com.ian.demo.mcp.remote.context.service.ContextService;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class ExecutorConfig {

    private final ContextService contextService;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("context-task-");
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setTaskDecorator(runnable -> {
            log.info("Decorate context task on thread {}", Thread.currentThread().getName());
            ContextSnapshot snapshot = contextService.getContextSnapshot();
            return () -> {
                log.info("Executing context task on thread {}", Thread.currentThread().getName());
                try {
                    contextService.restoreContextSnapshot(snapshot);
                    runnable.run();
                } finally {
                    contextService.clearContext();
                }
            };
        });
        executor.initialize();
        return executor;
    }

}
