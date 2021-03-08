package com.jgdeveloppement.pizza_serradifalco.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_app_id")
    @Expose
    val id : Int,
    @SerializedName("user_lastname")
    @Expose
    val lastName : String,
    @SerializedName("user_firstname")
    @Expose
    val firstName : String,
    @SerializedName("user_password")
    @Expose
    val password : String,
    @SerializedName("user_email")
    @Expose
    val email : String,
    @SerializedName("user_tel")
    @Expose
    val phone : String,
    @SerializedName("user_role")
    @Expose
    val role : String
)