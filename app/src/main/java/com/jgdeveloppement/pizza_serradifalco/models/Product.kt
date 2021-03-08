package com.jgdeveloppement.pizza_serradifalco.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("product_id")
    @Expose
    val id : Int,
    @SerializedName("product_large_price")
    @Expose
    val largePrice : Double,
    @SerializedName("product_url_image")
    @Expose
    val urlPic : String,
    @SerializedName("product_name")
    @Expose
    val name : String,
    @SerializedName("product_category")
    @Expose
    val category : String,
    @SerializedName("product_component")
    @Expose
    val component : String,
    @SerializedName("product_medium_price")
    @Expose
    val mediumPrice : Double
)