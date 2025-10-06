package com.example.eskikoprojectuts.model

import com.google.gson.annotations.SerializedName

data class Anak (
    @SerializedName("weight")
    var weight: Double?,
    @SerializedName("height")
    var height: Double?,
    @SerializedName("usia")
    var usia: Int?
)
