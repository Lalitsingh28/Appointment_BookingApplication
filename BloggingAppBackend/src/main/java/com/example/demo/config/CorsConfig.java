package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000") // Update with your frontend's URL
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Add any additional HTTP methods your application uses
            .allowedHeaders("*") // Allow all headers
            .allowCredentials(true); // Allow sending cookies with requests
    }
}