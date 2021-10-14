package com.close.svea.refactoringsample.network

import com.close.svea.refactoringsample.network.models.Places
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("meappid")
    fun getAllPlaces(
        @Query("meAppId") id: Int = 50,
        @Query("records") records: Int = 500
    ): Call<Places>
}