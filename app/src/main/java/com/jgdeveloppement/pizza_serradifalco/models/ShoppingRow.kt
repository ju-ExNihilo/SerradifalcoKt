package com.jgdeveloppement.pizza_serradifalco.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ShoppingRow(
    @SerializedName("product_name")
    @Expose
    var productName: String = "none",
    @SerializedName("product_size")
    @Expose
    var size: String = "Non défini",
    @SerializedName("extra")
    @Expose
    var extra: String = "Aucun",
    @SerializedName("quantity")
    @Expose
    var quantity: Int = 0,
    @SerializedName("product_price")
    @Expose
    var price: Double = 0.0)