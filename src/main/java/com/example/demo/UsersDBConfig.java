package com.example.demo;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.demo.users.repo",entityManagerFactoryRef = "usersEntityManagerFactory", transactionManagerRef = "usersTransactionManager")
public class UsersDBConfig {
    @Autowired
    Environment environment;

    @Bean
    @ConfigurationProperties(prefix = "users.datasource")
    public DataSource usersDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    PlatformTransactionManager usersTransactionManager(
        @Qualifier("usersEntityManagerFactory") LocalContainerEntityManagerFactoryBean usersEntityManagerFactory) {
        return new JpaTransactionManager(usersEntityManagerFactory.getObject());
    }

    @Bean
    LocalContainerEntityManagerFactoryBean usersEntityManagerFactory(
        @Qualifier("usersDataSource") DataSource usersDatasource, EntityManagerFactoryBuilder builder) {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect","org.hibernate.dialect.HSQLDialect");
        properties.put("hibernate.hbm2ddl.auto",environment.getProperty("hibernate.hbm2ddl.auto"));
        return builder.dataSource(usersDatasource).properties(properties).packages("com.example.demo.users.model").build();
    }
}