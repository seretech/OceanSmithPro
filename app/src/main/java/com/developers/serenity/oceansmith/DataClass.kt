package com.developers.serenity.oceansmith

import com.google.gson.annotations.SerializedName

data class DataClass(
    @SerializedName("Species Name")
    val name: String = "",

    @SerializedName("Species Illustration Photo")
    val img: SpeciesImage,

    @SerializedName("Habitat Impacts")
    val hb: String = "",
)

data class SpeciesImage(
    @SerializedName("src")
    val src : String,
)
