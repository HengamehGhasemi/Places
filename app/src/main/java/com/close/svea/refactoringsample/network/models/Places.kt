package com.close.svea.refactoringsample.network.models

import com.google.gson.annotations.SerializedName

data class Places (
    @SerializedName("place")
	val place : List<Place>,
    @SerializedName("total")
	val total : Int
)