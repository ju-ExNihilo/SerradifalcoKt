package com.jgdeveloppement.pizza_serradifalco.viewmodel

import androidx.lifecycle.ViewModel
import com.jgdeveloppement.pizza_serradifalco.repository.ProductRepository

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    fun getPizzaTomatoList() = productRepository.getPizzaTomatoList()
}