package com.bjsxt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 解决跨域问题，放在网关中
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*"); // 允许跨域访问的方法
        config.addAllowedOrigin("*"); // 允许跨域访问的域
        config.addAllowedHeader("*"); // 允许跨域的头

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config); // 所有路径都支持跨域

        return new CorsWebFilter(source);
    }
}
