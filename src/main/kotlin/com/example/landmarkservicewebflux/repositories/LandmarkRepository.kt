package com.example.landmarkservicewebflux.repositories

import com.example.landmarkservicewebflux.entities.Landmark
import java.util.UUID
import org.springframework.data.domain.PageRequest
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface LandmarkRepository : R2dbcRepository<Landmark, UUID> {

    fun findAllBy(pageRequest: PageRequest): Flux<Landmark>

    fun findByName(name: String): Mono<Landmark>

}
