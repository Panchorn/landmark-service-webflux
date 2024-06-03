package com.example.landmarkservicewebflux.routers

import com.example.landmarkservicewebflux.handlers.ErrorHandler
import com.example.landmarkservicewebflux.handlers.LandmarkHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class LandmarkRouter {

    @Bean
    fun landmarkRoutes(
        landmarkHandler: LandmarkHandler, errorHandler: ErrorHandler
    ): RouterFunction<ServerResponse> {
        return RouterFunctions.route().path("/api") { router ->
            router.POST("/landmarks", landmarkHandler::saveLandmark)
            router.GET("/landmarks", landmarkHandler::getLandmarks)
            router.GET("/landmarks/{name}", landmarkHandler::getLandmark)
        }.filter(errorHandler).build()
    }

}