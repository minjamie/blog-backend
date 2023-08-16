package com.example.blog.config.database;


import com.example.blog.BlogBackendApplication;
import com.example.blog.properties.DataSourceProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.example.blog.repository.user", "com.example.blog.repository.post",
                "com.example.blog.repository.comment", "com.example.blog.repository.like",
                "com.example.blog.repository.refreshToken"
        }
)
public class JpaConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("dataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.blog.domain"); // 엔티티 클래스가 포함된 패키지를 지정
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.use_sql_comment", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        em.setJpaPropertyMap(properties);
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
        return transactionManager;
    }
}
