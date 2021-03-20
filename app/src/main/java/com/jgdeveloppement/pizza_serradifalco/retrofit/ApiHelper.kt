package com.jgdeveloppement.pizza_serradifalco.retrofit


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

    //Address
    suspend fun getAllAddressByUserId(userId: Int) = apiService.getAllAddressByUserId("api", "AddressByUserId", userId)
    suspend fun insertAddress(address: HashMap<String, String>) = apiService.insertAddress(address)
}