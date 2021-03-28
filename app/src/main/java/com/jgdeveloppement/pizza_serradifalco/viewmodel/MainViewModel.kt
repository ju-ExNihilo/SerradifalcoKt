package com.jgdeveloppement.pizza_serradifalco.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jgdeveloppement.pizza_serradifalco.models.Product
import com.jgdeveloppement.pizza_serradifalco.models.Settings
import com.jgdeveloppement.pizza_serradifalco.models.ShoppingRow
import com.jgdeveloppement.pizza_serradifalco.repository.MainRepository
import com.jgdeveloppement.pizza_serradifalco.retrofit.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun getSettings() = mainRepository.getSettings()

    fun getPizzaDay() = mainRepository.getPizzaDay()

    //User
    fun getUser(email: String) = mainRepository.getUser(email)
    fun createUser(user: HashMap<String, String>) = mainRepository.createUser(user)
    fun updateUser(user: HashMap<String, String>) = mainRepository.updateUser(user)

    //Address
    fun getAllAddressByUserId(userId: Int) = mainRepository.getAllAddressByUserId(userId)
    fun deleteAddress(addressId: Int, userId: Int) = mainRepository.deleteAddress(addressId, userId)
    fun insertAddress(wayNumber : Int, way : String,  postCode : String, town : String, additionalAddress : String, userId : Int) =
        mainRepository.insertAddress(wayNumber, way, postCode, town, additionalAddress, userId)

    //Order
    fun insertNewOrder(order: HashMap<String, String>) = mainRepository.insertNewOrder(order)
    fun insertShoppingRow(orderId: Int, shopList : List<ShoppingRow>) = mainRepository.insertShoppingRow(orderId, shopList)

}