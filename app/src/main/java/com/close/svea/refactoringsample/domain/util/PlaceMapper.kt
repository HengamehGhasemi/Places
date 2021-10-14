package com.close.svea.refactoringsample.domain.util

import com.close.svea.refactoringsample.database.model.PlaceEntity
import com.close.svea.refactoringsample.network.models.Place

class PlaceMapper : DomainMapper<Place, PlaceEntity> {

    override fun mapToEntityModel(model: Place): PlaceEntity {
        return PlaceEntity(
            alias = model.alias,
            name = model.name,
            longitude = model.longitude,
            latitude = model.latitude,
            description = model.description,
            icon = model.icon
        )
    }

    override fun mapToDomainModel(model: PlaceEntity): Place {
        return Place(
            alias = model.alias,
            name = model.name,
            longitude = model.longitude,
            latitude = model.latitude,
            description = model.description,
            icon = model.icon
        )
    }

    fun toPlaceEntityList(initial: List<Place>): List<PlaceEntity>{
        return initial.map { mapToEntityModel(it) }
    }

    fun toPlaceList(initial: List<PlaceEntity>): List<Place>{
        return initial.map { mapToDomainModel(it) }
    }
}