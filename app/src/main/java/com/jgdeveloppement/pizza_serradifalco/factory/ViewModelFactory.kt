package com.jgdeveloppement.pizza_serradifalco.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jgdeveloppement.pizza_serradifalco.viewmodel.MainViewModel
import com.jgdeveloppement.pizza_serradifalco.repository.MainRepository
import com.jgdeveloppement.pizza_serradifalco.repository.ProductRepository
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.viewmodel.ProductViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }else if (modelClass.isAssignableFrom(ProductViewModel::class.java)){
            return ProductViewModel(ProductRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}