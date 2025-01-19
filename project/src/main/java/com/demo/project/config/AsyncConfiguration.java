package com.demo.project.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfiguration{
	
	@Bean(name = "taskExecutor")
	 Executor taskExecutor()
	{
		ThreadPoolTaskExecutor executor =  new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25);
		executor.setThreadNamePrefix("AsyncExecutor-");
		return executor;
	}

}
