package com.close.svea.refactoringsample.network.repository

import androidx.lifecycle.LiveData
import com.close.svea.refactoringsample.database.dao.PlaceDao
import com.close.svea.refactoringsample.database.model.PlaceEntity
import com.close.svea.refactoringsample.network.PlaceService
import com.close.svea.refactoringsample.network.models.Place
import com.close.svea.refactoringsample.domain.util.PlaceMapper
import kotlinx.coroutines.*
import javax.inject.Inject

class GetPlacesRepository @Inject constructor(
    private val placeDao: PlaceDao,
    private val placeService: PlaceService
    ) {

    private val mapper = PlaceMapper()

    suspend fun fetchAllPlaces() : List<Place> = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
        var response = placeService.getAllPlaces().execute().body()!!.place
        insertAllPlaces(mapper.toPlaceEntityList(response))
        return@withContext response
    }

    fun getAllPlaces():LiveData<List<PlaceEntity>> {
        return placeDao.observeAllPlaces()
    }

    private fun insertAllPlaces(placeListEntity:List<PlaceEntity>) {
        // insert all places to database
        CoroutineScope(Dispatchers.Default).launch {
            placeDao.insertAllPlaces(placeListEntity)
        }
    }
}