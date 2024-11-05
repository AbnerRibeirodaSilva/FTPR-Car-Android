package com.example.myapitest.model

import com.google.gson.annotations.SerializedName

data class CarConsult(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("value")
    var car: Car? = null
)