package com.jgdeveloppement.pizza_serradifalco.retrofit


class ApiHelper(private val apiService: ApiService) {

    suspend fun getSettings() = apiService.getSettings("api","Settings")

    suspend fun getPizzaDay() = apiService.getPizzaDay("api","PizzaDay")

    suspend fun getUser(email: String) = apiService.getUser("api","User", email)

    suspend fun createUser(user: HashMap<String, String>) = apiService.createUser(user)
}