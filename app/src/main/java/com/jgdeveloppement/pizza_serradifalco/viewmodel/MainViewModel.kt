package com.jgdeveloppement.pizza_serradifalco.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jgdeveloppement.pizza_serradifalco.models.Product
import com.jgdeveloppement.pizza_serradifalco.models.Settings
import com.jgdeveloppement.pizza_serradifalco.repository.MainRepository
import com.jgdeveloppement.pizza_serradifalco.retrofit.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getSettings() = mainRepository.getSettings()

    fun getPizzaDay() = mainRepository.getPizzaDay()

    fun getUser(email: String) = mainRepository.getUser(email)

    fun createUser(user: HashMap<String, String>) = mainRepository.createUser(user)
}