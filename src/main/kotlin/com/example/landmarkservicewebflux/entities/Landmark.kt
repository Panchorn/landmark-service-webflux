package com.example.landmarkservicewebflux.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable
import java.util.UUID
import lombok.Getter
import org.springframework.data.domain.Persistable

@Entity
@Table(name = "landmark")
@Getter
data class Landmark(
    @Id
    @org.springframework.data.annotation.Id
    private var id: UUID?,
    @Column(name = "name") val name: String,
    @Column(name = "country") val country: String,
    @Column(name = "province") val province: String,
    @Column(name = "type") val type: String,
    @Column(name = "rating") val rating: Double
) : Serializable, Persistable<UUID> {

    override fun getId(): UUID {
        return UUID.randomUUID()
    }

    override fun isNew(): Boolean {
        return id == null
    }

}
