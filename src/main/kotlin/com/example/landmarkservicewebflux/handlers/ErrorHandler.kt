package com.example.landmarkservicewebflux.handlers

import java.lang.invoke.MethodHandles
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerFilterFunction
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ErrorHandler : HandlerFilterFunction<ServerResponse, ServerResponse> {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }

    override fun filter(
        request: ServerRequest, next: HandlerFunction<ServerResponse>
    ): Mono<ServerResponse> {
        return next.handle(request).doOnError { logger.error("Error occurred", it) }
            .onErrorResume(ChangeSetPersister.NotFoundException::class.java) { handleNotFound() }
    }

    private fun handleNotFound(): Mono<ServerResponse> {
        return ServerResponse.notFound().build()
    }

}