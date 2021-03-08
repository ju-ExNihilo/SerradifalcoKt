package com.jgdeveloppement.pizza_serradifalco.retrofit

import com.jgdeveloppement.pizza_serradifalco.models.Product

class ApiHelper(private val apiService: ApiService) {
    suspend fun getSettings() = apiService.getSettings("api","Settings")

    suspend fun getPizzaDay() = apiService.getPizzaDay("api","PizzaDay")

    suspend fun getUser(email: String) = apiService.getUser("api","User", email)
}