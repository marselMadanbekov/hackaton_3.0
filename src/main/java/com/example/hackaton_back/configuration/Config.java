package com.example.hackaton_back.configuration;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {
    @Value("${uploads_path}")
    private String uploadPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Добавьте ваш ResourceHandler здесь
        registry
                .addResourceHandler("/uploads/**")  // URL-путь, по которому будут доступны ресурсы
                .addResourceLocations("file:" + uploadPath); // Физический путь к директории с ресурсами
    }
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        factory.setMaxFileSize(DataSize.ofMegabytes(100L));
        factory.setMaxRequestSize(DataSize.ofMegabytes(110L));

        return factory.createMultipartConfig();
    }
}
