package com.jgdeveloppement.pizza_serradifalco.repository

import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getSettings() = apiHelper.getSettings()

    suspend fun getPizzaDay() = apiHelper.getPizzaDay()

    suspend fun getUser(email: String) = apiHelper.getUser(email)
}