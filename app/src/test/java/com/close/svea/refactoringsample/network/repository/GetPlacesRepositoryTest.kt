package com.close.svea.refactoringsample.network.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.close.svea.refactoringsample.database.AppDatabase
import com.close.svea.refactoringsample.database.dao.PlaceDao
import com.close.svea.refactoringsample.database.model.PlaceEntity
import com.close.svea.refactoringsample.network.PlaceService
import com.close.svea.refactoringsample.network.models.Place
import com.close.svea.refactoringsample.util.ServerMockResponse
import com.close.svea.refactoringsample.util.getOrAwaitValue
import com.google.gson.GsonBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GetPlacesRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    //Dependencies
    private var mockWebServer: MockWebServer = MockWebServer()
    private lateinit var baseUrl: HttpUrl
    private lateinit var placeService: PlaceService
    private lateinit var database: AppDatabase
    private lateinit var dao: PlaceDao

    //Under the Test
    private lateinit var getPlacesRepository: GetPlacesRepository

    @Before
    fun setup() {

        System.setProperty("javax.net.ssl.trustStore", "NONE")
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/meAppId/")
        placeService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(PlaceService::class.java)

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()



        dao = database.placeDao()
        getPlacesRepository = GetPlacesRepository(dao,placeService)
    }

    @org.junit.Test
    fun fetchAllPlaces() = runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(ServerMockResponse)
            )
            //first we should check the database is empty
            assert(dao.observeAllPlaces().getOrAwaitValue().isEmpty())

            val result = getPlacesRepository.fetchAllPlaces()
            //check the network result is taken properly
            assert(result[0].name == "TestplatsförMO")

    }

    @Test
    fun getAllPlaces()= runBlocking {

            //create 2 place and push them in database
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

            val placeList = listOf(placeItem1, placeItem2)

            dao.insertAllPlaces(placeList)

            val allPlaces = getPlacesRepository.getAllPlaces().getOrAwaitValue()

            assert(allPlaces.containsAll(placeList))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        database.close()
    }
}