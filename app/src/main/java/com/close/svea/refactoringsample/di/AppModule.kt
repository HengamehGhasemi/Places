package com.close.svea.refactoringsample.di

import android.content.Context
import androidx.room.Room
import com.close.svea.refactoringsample.database.AppDatabase
import com.close.svea.refactoringsample.database.dao.PlaceDao
import com.close.svea.refactoringsample.network.PlaceService
import com.close.svea.refactoringsample.network.repository.GetPlacesRepository
import com.close.svea.refactoringsample.util.ApiServicesUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePlacesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

    @Singleton
    @Provides
    fun provideDefaultPlaceRepository(
        dao: PlaceDao,
        api: PlaceService
    ) = GetPlacesRepository(dao, api)

    @Singleton
    @Provides
    fun providePlaceDao(
        database: AppDatabase
    ) = database.placeDao()

    @Singleton
    @Provides
    fun providePlaceService(): PlaceService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiServicesUrl)
            .build()
            .create(PlaceService::class.java)
    }
}
