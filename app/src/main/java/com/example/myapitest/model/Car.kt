package com.example.myapitest.model

import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("imageUrl")
    var imageUrl: String = "",
    @SerializedName("year")
    var year: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("licence")
    var licence: String = ""
)