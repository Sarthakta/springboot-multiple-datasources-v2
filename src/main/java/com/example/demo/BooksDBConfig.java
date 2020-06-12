package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.demo.books.repo",entityManagerFactoryRef = "booksEntityManagerFactory", transactionManagerRef = "booksTransactionManager")
public class BooksDBConfig {
    @Autowired
    Environment environment;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "books.datasource")
    public DataSource booksDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    PlatformTransactionManager booksTransactionManager(
        @Qualifier("booksEntityManagerFactory") LocalContainerEntityManagerFactoryBean booksEntityManagerFactory) {
        return new JpaTransactionManager(booksEntityManagerFactory.getObject());
    }

    @Bean
    LocalContainerEntityManagerFactoryBean booksEntityManagerFactory(
        @Qualifier("booksDataSource") DataSource bookssDatasource, EntityManagerFactoryBuilder builder) {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto",environment.getProperty("hibernate.hbm2ddl.auto"));
        return builder.dataSource(bookssDatasource).properties(properties).packages("com.example.demo.books.model").build();

    }


}