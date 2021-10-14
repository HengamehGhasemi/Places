package com.close.svea.refactoringsample.database.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_table")
data class PlaceEntity(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "alias")
    var alias: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name ="longitude")
    var longitude: Double,

    @ColumnInfo(name ="latitude")
    var latitude: Double,

    @ColumnInfo(name ="description")
    var description: String,

    @ColumnInfo(name ="icon")
    var icon: String

)