package com.code.cetboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Configuration
public class LogConfig {

    @Bean(name = "logger")
    public Logger logger() {
        return LoggerFactory.getLogger("com.code.cetboot");
    }
}
