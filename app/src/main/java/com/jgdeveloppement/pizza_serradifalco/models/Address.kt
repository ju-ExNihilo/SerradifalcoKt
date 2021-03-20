package com.jgdeveloppement.pizza_serradifalco.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("user_address_id")
    @Expose
    val id : Int,
    @SerializedName("address_number")
    @Expose
    val wayNumber : Int,
    @SerializedName("address_way")
    @Expose
    val way : String,
    @SerializedName("address_post_code")
    @Expose
    val postCode : String,
    @SerializedName("address_town")
    @Expose
    val town : String,
    @SerializedName("address_complement")
    @Expose
    val additionalAddress : String,
    @SerializedName("user_app_id_fk")
    @Expose
    val userId : Int,

)