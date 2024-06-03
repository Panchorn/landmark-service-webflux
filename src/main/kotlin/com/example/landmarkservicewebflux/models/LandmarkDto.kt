package com.example.landmarkservicewebflux.models

import java.util.UUID

data class LandmarkDto(
    val id: UUID?,
    val name: String,
    val country: String,
    val province: String,
    val type: String,
    val rating: Double
)