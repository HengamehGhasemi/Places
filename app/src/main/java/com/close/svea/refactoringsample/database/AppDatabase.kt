package com.close.svea.refactoringsample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.close.svea.refactoringsample.database.dao.PlaceDao
import com.close.svea.refactoringsample.database.model.PlaceEntity

@Database(
    entities = [PlaceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun placeDao() : PlaceDao

    companion object {
        private var instance: AppDatabase? = null
        @Synchronized
        fun get(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .build()
            }
            return instance!!
        }
    }
}