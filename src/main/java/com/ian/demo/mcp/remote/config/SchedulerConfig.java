package com.ian.demo.mcp.remote.config;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.core.scheduler.Schedulers.Factory;

@Log4j2
@RequiredArgsConstructor
@Configuration
public class SchedulerConfig {

    private final Executor taskExecutor;

    @PostConstruct
    public void init() {
        Schedulers.setFactory(new Factory() {
            @Override
            public Scheduler newBoundedElastic(int threadCap, int queuedTaskCap,
                ThreadFactory threadFactory, int ttlSeconds) {
                return Schedulers.fromExecutor(taskExecutor);
            }
        });
    }

}
