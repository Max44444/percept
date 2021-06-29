package bsa.java.concurrency.config;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
@Log4j2
public class AsyncConfiguration extends AsyncConfigurerSupport {

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        val executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.initialize();
        return executor;
    }

    @Override
    @Nullable
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, obj) -> {
            log.error("Exception Caught in Thread - " + Thread.currentThread().getName());
            log.error("Exception message - " + throwable.getMessage());
            log.error("Method name - " + method.getName());
            for (Object param : obj) {
                log.error("Parameter value - " + param);
            }
        };
    }

}
