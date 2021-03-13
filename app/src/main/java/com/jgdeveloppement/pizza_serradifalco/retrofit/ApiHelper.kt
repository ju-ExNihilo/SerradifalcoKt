package com.jgdeveloppement.pizza_serradifalco.retrofit


class ApiHelper(private val apiService: ApiService) {

    // Settings
    suspend fun getSettings() = apiService.getSettings("api","Settings")

    // Pizza
    suspend fun getPizzaDay() = apiService.getPizzaDay("api","PizzaDay")
    suspend fun getPizzaTomato() = apiService.getPizzaTomato("api","Tomate")

    // User
    suspend fun getUser(email: String) = apiService.getUser("api","User", email)
    suspend fun createUser(user: HashMap<String, String>) = apiService.createUser(user)
}