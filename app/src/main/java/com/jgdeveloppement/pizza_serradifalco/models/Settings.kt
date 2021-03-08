package com.jgdeveloppement.pizza_serradifalco.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Settings(
    @SerializedName("settings_id")
    @Expose
    val id : Int,
    @SerializedName("tomate_url")
    @Expose
    val tomatoUrl: String,
    @SerializedName("prenium_url")
    @Expose
    val premiumUrl: String,
    @SerializedName("blanche_url")
    @Expose
    val blancheUrl: String,
    @SerializedName("carte_url")
    @Expose
    val menuUrl: String,
    @SerializedName("dessert1_url")
    @Expose
    val dessert1Url: String,
    @SerializedName("dessert2_url")
    @Expose
    val dessert2Url: String,
    @SerializedName("dessert3_url")
    @Expose
    val dessert3Url: String

)