package com.close.svea.refactoringsample.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.close.svea.refactoringsample.database.AppDatabase
import com.close.svea.refactoringsample.database.model.PlaceEntity
import com.close.svea.refactoringsample.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PlaceDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //Dependencies
    private lateinit var database: AppDatabase
    //Under The Test
    private lateinit var dao: PlaceDao

    @Before
    fun setup(){

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.placeDao()
    }

    @Test
    fun insertAllPlaces() = runBlocking {

        val placeItem1 = PlaceEntity(
            "a353a831-5d53-4cc2-93f3-6c8a3df1e3c9",
            "TestplatsförMO",
            59.651342017, 17.9312360,
            "Kommerinomkort",
            "/image/83423179e3a34332857e47f7cacdf703_icon_1416923254311")

        val placeItem2 = PlaceEntity(
            "d2ee4e66-cfff-4522-a999-99b0460c535b",
            "Arlanda-PontusintheAir",
            59.6513424, 17.931236799999965,
            "Kommande",
            "/image/db3f9210-d1dd-4b37-bb83-e23ebe8b422c")

        val placeList = listOf(placeItem1,placeItem2)

        dao.insertAllPlaces(placeList)

        val allPlaces = dao.observeAllPlaces().getOrAwaitValue()

        assertThat(allPlaces).contains(placeList)

    }

    @After
    fun teardown(){
        database.close()
    }
}