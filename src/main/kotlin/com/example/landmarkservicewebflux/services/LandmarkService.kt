package com.example.landmarkservicewebflux.services

import com.example.landmarkservicewebflux.entities.Landmark
import com.example.landmarkservicewebflux.models.LandmarkDto
import com.example.landmarkservicewebflux.repositories.LandmarkRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class LandmarkService(
    private val landmarkRepository: LandmarkRepository
) {

    fun saveLandmark(landmarkDto: LandmarkDto): Mono<LandmarkDto> {
        val entity = buildEntity(landmarkDto)
        return landmarkRepository.save(entity)
            .map { buildDto(it) }
    }

    fun getAllLandmarks(pageRequest: PageRequest) : Mono<PageImpl<Landmark>> {
        return landmarkRepository.findAllBy(pageRequest).collectList()
            .zipWith(this.landmarkRepository.count()).map { page ->
                val data = page.t1
                val total = page.t2
                PageImpl(data, pageRequest, total)
            }
    }

    fun getLandmarks(name: String): Mono<Landmark> {
        return landmarkRepository.findByName(name)
            .switchIfEmpty(Mono.error(ChangeSetPersister.NotFoundException()))
    }

    private fun buildEntity(landmarkDto: LandmarkDto): Landmark {
        return Landmark(
            id = landmarkDto.id,
            name = landmarkDto.name,
            country = landmarkDto.country,
            province = landmarkDto.province,
            type = landmarkDto.type,
            rating = landmarkDto.rating
        )
    }

    private fun buildDto(landmark: Landmark): LandmarkDto {
        return LandmarkDto(
            id = landmark.id,
            name = landmark.name,
            country = landmark.country,
            province = landmark.province,
            type = landmark.type,
            rating = landmark.rating
        )
    }

}
