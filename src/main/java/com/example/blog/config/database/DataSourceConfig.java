package com.example.blog.config.database;

import com.example.blog.properties.DataSourceProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties({DataSourceProperty.class})
public class DataSourceConfig {
    private final DataSourceProperty dataSourceProperty;
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(dataSourceProperty.getUsername());
        dataSource.setPassword(dataSourceProperty.getPassword());
        dataSource.setUrl(dataSourceProperty.getUrl());
        return dataSource;
    }
}
