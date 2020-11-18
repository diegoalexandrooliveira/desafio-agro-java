package br.com.diegoalexandro.desafioagrojava;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Component
class PostgreSQLUtil {

    private final static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:13");

    static {
        postgreSQLContainer.start();
    }

    @Bean
    DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName("org.postgresql.Driver")
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .url(postgreSQLContainer.getJdbcUrl())
                .build();
    }
}
