package com.example.landmarkservicewebflux.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("spring.r2dbc")
class R2dbcProperties {
    lateinit var host: String
    lateinit var port: Number
    lateinit var username: String
    lateinit var password: String
    lateinit var database: String
}