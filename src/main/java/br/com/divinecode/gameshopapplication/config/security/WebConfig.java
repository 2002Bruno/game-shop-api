package br.com.divinecode.gameshopapplication.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Value("${spring.graphql.cors.allowed-origin-patterns:default}")
    private String corsOriginPatterns = "";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigin = corsOriginPatterns.split(",");
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(allowedOrigin)
                .allowCredentials(true);
    }
}
