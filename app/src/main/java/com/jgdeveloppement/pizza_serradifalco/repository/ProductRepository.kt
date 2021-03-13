package com.jgdeveloppement.pizza_serradifalco.repository

import androidx.lifecycle.liveData
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.Resource
import kotlinx.coroutines.Dispatchers

class ProductRepository(private val apiHelper: ApiHelper) {

    fun getProductList(type: String) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getProductList(type)))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message?: "Error Occurred!"))
        }
    }

    fun getPizzaById(id: Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiHelper.getPizzaById(id)))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message?: "Error Occurred!"))
        }
    }
}