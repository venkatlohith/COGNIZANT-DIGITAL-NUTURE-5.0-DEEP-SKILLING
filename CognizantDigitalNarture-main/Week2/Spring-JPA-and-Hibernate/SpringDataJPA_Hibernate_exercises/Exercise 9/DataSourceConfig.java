// ===== application.properties (multiple data sources) =====

# Primary DataSource (H2 in-memory)
spring.datasource.primary.url=jdbc:h2:mem:testdb
spring.datasource.primary.driver-class-name=org.h2.Driver
spring.datasource.primary.username=sa
spring.datasource.primary.password=password

# Secondary DataSource (example: another H2 or MySQL)
spring.datasource.secondary.url=jdbc:h2:mem:secondarydb
spring.datasource.secondary.driver-class-name=org.h2.Driver
spring.datasource.secondary.username=sa
spring.datasource.secondary.password=password

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true


// ===== DataSourceConfig.java =====

package com.example.EmployeeManagementSystem.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // Primary DataSource properties
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    // Primary DataSource
    @Bean
    @Primary
    public DataSource primaryDataSource(
            @Qualifier("primaryDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    // Secondary DataSource properties
    @Bean
    @ConfigurationProperties("spring.datasource.secondary")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    // Secondary DataSource
    @Bean
    public DataSource secondaryDataSource(
            @Qualifier("secondaryDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }
}
