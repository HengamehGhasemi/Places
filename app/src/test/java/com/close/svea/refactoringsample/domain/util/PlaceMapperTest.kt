package com.close.svea.refactoringsample.domain.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.close.svea.refactoringsample.database.model.PlaceEntity
import com.close.svea.refactoringsample.network.models.Place
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlaceMapperTest {

    private lateinit var mapper: PlaceMapper
    private lateinit var placeModelItem: Place
    private lateinit var placeEntity: PlaceEntity

    @Before
    fun setUp() {

     mapper = PlaceMapper()
     placeModelItem = Place(
         "a353a831-5d53-4cc2-93f3-6c8a3df1e3c9",
         "TestplatsförMO",
         59.651342017, 17.9312360,
         "Kommerinomkort",
         "/image/83423179e3a34332857e47f7cacdf703_icon_1416923254311")
     placeEntity = PlaceEntity(
         "a353a831-5d53-4cc2-93f3-6c8a3df1e3c9",
         "TestplatsförMO",
         59.651342017, 17.9312360,
         "Kommerinomkort",
         "/image/83423179e3a34332857e47f7cacdf703_icon_1416923254311")


    }

    @After
    fun tearDown() {
    }

    @Test
    fun toPlaceEntityList() {

       val result = mapper.toPlaceEntityList(listOf(placeModelItem))
        assert(result[0] is PlaceEntity)
        assert(result.isNotEmpty())
        assert(result[0].name == "TestplatsförMO")
    }

    @Test
    fun toPlaceList() {
        val result = mapper.toPlaceList(listOf(placeEntity))
        assert(result[0] is Place)
        assert(result.isNotEmpty())
    }
}