package com.example.blog.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperty {
    private String username;
    private String password;
    private String url;
}
