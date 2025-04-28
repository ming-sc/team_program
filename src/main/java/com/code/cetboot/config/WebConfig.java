package com.code.cetboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : IMG
 * @create : 2025/4/28
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.filePath}")
    private String filePath;

    @Value("${webPath}")
    private String webPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(webPath + "**")
                .addResourceLocations("file:///" + filePath);
    }
}
