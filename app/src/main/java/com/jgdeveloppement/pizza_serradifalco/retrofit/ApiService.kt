package com.jgdeveloppement.pizza_serradifalco.retrofit

import com.jgdeveloppement.pizza_serradifalco.models.Address
import com.jgdeveloppement.pizza_serradifalco.models.Product
import com.jgdeveloppement.pizza_serradifalco.models.Settings
import com.jgdeveloppement.pizza_serradifalco.models.User
import retrofit2.http.*

interface ApiService{

    // Setting
    @GET("/index.php")
    suspend fun getSettings(@Query("action")action : String, @Query("type") type : String): Settings

    // Product
    @GET("/index.php")
    suspend fun getPizzaDay(@Query("action")action : String, @Query("type") type : String): Product

    @GET("/index.php")
    suspend fun getPizzaById(@Query("action")action : String, @Query("type") type : String, @Query("id") id : Int): Product

    @GET("/index.php")
    suspend fun getProductList(@Query("action")action : String, @Query("type") type : String): List<Product>

    // User
    @GET("/index.php")
    suspend fun getUser(@Query("action")action : String, @Query("type") type : String, @Query("email") email : String): User

    @FormUrlEncoded
    @POST("/index.php?action=api&type=AddUser")
    suspend fun createUser(@FieldMap body: HashMap<String, String>): User

    //Address
    @GET("/index.php")
    suspend fun getAllAddressByUserId(@Query("action")action : String, @Query("type") type : String, @Query("id") id : Int): List<Address>

    @FormUrlEncoded
    @POST("/index.php?action=api&type=InsertAddress")
    suspend fun insertAddress(@FieldMap body: HashMap<String, String>): Address
}