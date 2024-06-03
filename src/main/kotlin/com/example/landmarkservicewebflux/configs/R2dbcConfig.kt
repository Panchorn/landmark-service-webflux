package com.example.landmarkservicewebflux.configs

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.support.DefaultTransactionDefinition

@Configuration
internal class R2dbcConfig(
    private val r2dbcProperties: R2dbcProperties
) {
    @Bean
    fun connectionFactory(): PostgresqlConnectionFactory {
        return PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder()
                .host(r2dbcProperties.host)
                .port(r2dbcProperties.port.toInt())
                .username(r2dbcProperties.username)
                .password(r2dbcProperties.password)
                .database(r2dbcProperties.database)
                .build()
        )
    }

    @Bean
    fun databaseClient(connectionFactory: PostgresqlConnectionFactory): DatabaseClient {
        return DatabaseClient.create(connectionFactory)
    }

    @Bean
    fun reactiveTransactionManager(factory: PostgresqlConnectionFactory): ReactiveTransactionManager {
        return R2dbcTransactionManager(factory)
    }

    @Bean
    fun transactionalOperator(transactionManager: ReactiveTransactionManager): TransactionalOperator {
        return TransactionalOperator.create(transactionManager, DefaultTransactionDefinition())
    }
}