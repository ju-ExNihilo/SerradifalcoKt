package com.jgdeveloppement.pizza_serradifalco.retrofit

import com.jgdeveloppement.pizza_serradifalco.models.Product
import com.jgdeveloppement.pizza_serradifalco.models.Settings
import com.jgdeveloppement.pizza_serradifalco.models.User
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService{

    @GET("/index.php")
    suspend fun getSettings(@Query("action")action : String, @Query("type") type : String): Settings

    @GET("/index.php")
    suspend fun getPizzaDay(@Query("action")action : String, @Query("type") type : String): Product

    @GET("/index.php")
    suspend fun getUser(@Query("action")action : String, @Query("type") type : String, @Query("email") email : String): User
}