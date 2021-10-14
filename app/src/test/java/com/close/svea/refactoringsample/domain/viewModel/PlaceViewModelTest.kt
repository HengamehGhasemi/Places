package com.close.svea.refactoringsample.domain.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.close.svea.refactoringsample.database.AppDatabase
import com.close.svea.refactoringsample.database.dao.PlaceDao
import com.close.svea.refactoringsample.database.model.PlaceEntity
import com.close.svea.refactoringsample.network.PlaceService
import com.close.svea.refactoringsample.network.models.Place
import com.close.svea.refactoringsample.network.repository.GetPlacesRepository
import com.close.svea.refactoringsample.util.ServerMockResponse
import com.close.svea.refactoringsample.util.getOrAwaitValue
import com.google.gson.GsonBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class PlaceViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    //Dependencies
    private var mockWebServer: MockWebServer = MockWebServer()
    private lateinit var baseUrl: HttpUrl
    private lateinit var placeService: PlaceService
    private lateinit var database: AppDatabase
    private lateinit var dao: PlaceDao
    private lateinit var viewModel: PlaceViewModel

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


        viewModel = PlaceViewModel(getPlacesRepository)
    }
    /**
     * 1. Get some Places from the network and check if the fetch function
     * is working well
     * 2. Get some Places from the network and put it in database and check
     * is the insertion call is working well or not
     */
    @org.junit.Test
    fun fetchAllPlaces() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(ServerMockResponse)
        )
        //first we should check the database is empty

        assert(dao.observeAllPlaces().getOrAwaitValue().isEmpty())

        viewModel.fetchAllPlaces()

        delay(2000)

        //check the data base is not empty
        assert(dao.observeAllPlaces().getOrAwaitValue()[0].name == "TestplatsförMO")
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        database.close()
    }
}