package com.close.svea.refactoringsample.domain.util

interface DomainMapper <Entity, NetworkModel> {

    fun mapToEntityModel(model: Entity): NetworkModel
    fun mapToDomainModel(model: NetworkModel): Entity

}