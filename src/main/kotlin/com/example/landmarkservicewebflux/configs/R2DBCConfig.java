package com.example.landmarkservicewebflux.configs;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Configuration
public class R2DBCConfig {
    @Value("${spring.data.postgresql.host}")
    private String host;

    @Value("${spring.data.postgresql.port}")
    private int port;

    @Value("${spring.data.postgresql.username}")
    private String username;

    @Value("${spring.data.postgresql.password}")
    private String password;

    @Value("${spring.data.postgresql.database}")
    private String database;

    @Bean
    public PostgresqlConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
            .host(host)
            .port(port)
            .username(username)
            .password(password)
            .database(database)
            .build());
    }

    @Bean
    public DatabaseClient databaseClient(PostgresqlConnectionFactory connectionFactory) {
        return DatabaseClient.create(connectionFactory);
    }

    @Bean
    public ReactiveTransactionManager reactiveTransactionManager(PostgresqlConnectionFactory factory) {
        return new R2dbcTransactionManager(factory);
    }

    @Bean
    public TransactionalOperator transactionalOperator(ReactiveTransactionManager transactionManager) {
        return TransactionalOperator.create(transactionManager, new DefaultTransactionDefinition());
    }

}
