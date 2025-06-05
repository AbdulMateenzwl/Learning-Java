package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "app.pagination")
@Getter
@Setter
public class PaginationConfig {
    private int defaultPageSize;
    private int maxPageSize;
}
