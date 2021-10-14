package com.close.svea.refactoringsample.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.close.svea.refactoringsample.database.model.PlaceEntity
import dagger.Provides


@Dao

interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlaces(places: List<PlaceEntity>) : LongArray

    @Query("SELECT * FROM  place_table")
    fun observeAllPlaces() : LiveData<List<PlaceEntity>>
}