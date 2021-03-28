package com.jgdeveloppement.pizza_serradifalco.repository

import androidx.lifecycle.liveData
import com.jgdeveloppement.pizza_serradifalco.models.ShoppingRow
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.Resource
import kotlinx.coroutines.Dispatchers

class MainRepository(private val apiHelper: ApiHelper) {

    fun getSettings() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getSettings()))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message?: "Error Occurred!"))
        }
    }

    fun getPizzaDay() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getPizzaDay()))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message?: "Error Occurred!"))
        }
    }

    fun getUser(email: String) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getUser(email)))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message?: "Error Occurred!"))
        }
    }

    fun createUser(user: HashMap<String, String>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data= apiHelper.createUser(user)))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error Occurred"))
        }
    }

    fun updateUser(user: HashMap<String, String>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data= apiHelper.updateUser(user)))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error Occurred"))
        }
    }

    //Address
    fun getAllAddressByUserId(userId: Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getAllAddressByUserId(userId)))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message?: "Error Occurred!"))
        }
    }

    fun insertAddress(wayNumber : Int, way : String,  postCode : String, town : String, additionalAddress : String, userId : Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data= apiHelper.insertAddress(wayNumber, way, postCode, town, additionalAddress, userId)))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error Occurred"))
        }
    }

    fun deleteAddress(addressId: Int, userId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.deleteAddressById(addressId, userId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    //Order
    fun insertNewOrder(order: HashMap<String, String>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.insertNewOrder(order)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun insertShoppingRow(orderId: Int, shopList : List<ShoppingRow>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.insertShoppingRow(orderId, shopList)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}