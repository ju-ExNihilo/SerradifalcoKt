package com.jgdeveloppement.pizza_serradifalco.retrofit

import com.jgdeveloppement.pizza_serradifalco.models.*
import okhttp3.ResponseBody
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

    @FormUrlEncoded
    @POST("/index.php?action=api&type=UpdateUserApi")
    suspend fun updateUser(@FieldMap body: HashMap<String, String>): User

    //Address
    @GET("/index.php")
    suspend fun getAllAddressByUserId(@Query("action")action : String, @Query("type") type : String, @Query("id") id : Int): List<Address>

    @FormUrlEncoded
    @POST("/index.php?action=api&type=DeleteAddress")
    suspend fun deleteAddressById(@Field("address_id") id : Int, @Field("user_id") userId : Int):  List<Address>

    @FormUrlEncoded
    @POST("/index.php?action=api&type=InsertAddress")
    suspend fun insertAddress(@Field("address_number") wayNumber : Int, @Field("address_way") way : String,
                              @Field("address_post_code") postCode : String, @Field("address_town") town : String,
                              @Field("address_additional") additionalAddress : String, @Field("user_id") userId : Int): List<Address>

    //Order
    @FormUrlEncoded
    @POST("/index.php?action=api&type=InsertOrderApi")
    suspend fun insertNewOrder(@FieldMap body: HashMap<String, String>): Int

    @FormUrlEncoded
    @POST("/index.php?action=api&type=InsertShopRowApi")
    suspend fun insertShoppingRow(@Field("order_id") orderId : Int, @Field("shop_list") shopList : String): ResponseBody
}