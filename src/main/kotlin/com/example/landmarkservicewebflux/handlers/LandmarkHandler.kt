package com.example.landmarkservicewebflux.handlers

import com.example.landmarkservicewebflux.entities.Landmark
import com.example.landmarkservicewebflux.models.LandmarkDto
import com.example.landmarkservicewebflux.services.LandmarkService
import java.lang.invoke.MethodHandles
import kotlin.jvm.optionals.getOrElse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class LandmarkHandler(
    private val landmarkService: LandmarkService
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }

    fun saveLandmark(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.bodyToMono(LandmarkDto::class.java)
            .flatMap { landmarkService.saveLandmark(it) }
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .doOnError { logger.error("Error during inserting landmarks", it) }
    }

    fun getLandmarks(serverRequest: ServerRequest): Mono<ServerResponse> {
        val page = serverRequest.queryParam("page").getOrElse { "0" }.let { Integer.parseInt(it) }
        val size = serverRequest.queryParam("size").getOrElse { "20" }.let { Integer.parseInt(it) }
        val pageRequest = PageRequest.of(page, size)
        return landmarkService.getAllLandmarks(pageRequest)
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .doOnError { logger.error("Error during getting landmarks", it) }
    }

    fun getLandmark(serverRequest: ServerRequest): Mono<ServerResponse> {
        val name = serverRequest.pathVariable("name")
        return landmarkService.getLandmarks(name=name)
            .flatMap { ServerResponse.ok().body(BodyInserters.fromValue(it)) }
            .doOnError { logger.error("Error during getting landmark by name", it) }
    }

}