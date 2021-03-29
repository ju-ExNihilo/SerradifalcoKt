package com.jgdeveloppement.pizza_serradifalco.injection

import com.jgdeveloppement.pizza_serradifalco.factory.ViewModelFactory
import com.jgdeveloppement.pizza_serradifalco.repository.MainRepository
import com.jgdeveloppement.pizza_serradifalco.repository.ProductRepository
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder

object Injection {

    private fun provideMainRepository() : MainRepository {
        val apiHelper = ApiHelper(RetrofitBuilder.apiService)
        return MainRepository(apiHelper)
    }

    private fun provideProductRepository() : ProductRepository {
        val apiHelper = ApiHelper(RetrofitBuilder.apiService)
        return ProductRepository(apiHelper)
    }

    fun provideMainViewModelFactory() : ViewModelFactory {
        val mainRepository = provideMainRepository()
        return ViewModelFactory(mainRepository)
    }

    fun provideProductViewModelFactory() : ViewModelFactory {
        val productRepository = provideProductRepository()
        return ViewModelFactory(productRepository)
    }

}