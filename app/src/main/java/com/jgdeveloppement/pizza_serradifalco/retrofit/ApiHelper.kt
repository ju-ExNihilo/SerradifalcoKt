package com.jgdeveloppement.pizza_serradifalco.retrofit

import com.jgdeveloppement.pizza_serradifalco.models.ShoppingRow


class ApiHelper(private val apiService: ApiService) {

    // Settings
    suspend fun getSettings() = apiService.getSettings("api","Settings")

    // Pizza
    suspend fun getPizzaDay() = apiService.getPizzaDay("api","PizzaDay")
    suspend fun getPizzaById(id: Int) = apiService.getPizzaById("api","OneProduct", id)
    suspend fun getProductList(type: String) = apiService.getProductList("api", type)

    // User
    suspend fun getUser(email: String) = apiService.getUser("api","User", email)
    suspend fun createUser(user: HashMap<String, String>) = apiService.createUser(user)
    suspend fun updateUser(user: HashMap<String, String>) = apiService.updateUser(user)

    //Address
    suspend fun getAllAddressByUserId(userId: Int) = apiService.getAllAddressByUserId("api", "AddressByUserId", userId)
    suspend fun deleteAddressById(addressId: Int, userId: Int) = apiService.deleteAddressById(addressId, userId)
    suspend fun insertAddress(wayNumber : Int, way : String,  postCode : String, town : String, additionalAddress : String, userId : Int) =
        apiService.insertAddress(wayNumber, way, postCode, town, additionalAddress, userId)

    //Order
    suspend fun insertNewOrder(order: HashMap<String, String>) = apiService.insertNewOrder(order)
    suspend fun insertShoppingRow(orderId: Int, shopList : List<ShoppingRow>) = apiService.insertShoppingRow(orderId, shopList)
}