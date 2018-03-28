package com.dkom.atm.configuration;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.dkom.atm.repository")
@EntityScan(
        value = "com.dkom.atm.entity",
        basePackageClasses = Jsr310JpaConverters.class)
public class DatabaseConfig {

}
