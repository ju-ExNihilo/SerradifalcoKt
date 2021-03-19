package com.jgdeveloppement.pizza_serradifalco.utils

import com.jgdeveloppement.pizza_serradifalco.models.ShoppingRow

object UserData {
    var userId : Int? = null
    var userFirstName : String? = null
    var userLastName : String? = null
    var userPhone : String? = null
    var shoppingRowList: MutableList<ShoppingRow> = mutableListOf()

    fun getNumberProduct(): Int{
        var quantityP = 0
        for (shop in shoppingRowList){
            quantityP += shop.quantity
        }
        return quantityP
    }

    fun getShopTotalPrice(): Double{
        var total = 0.0
        for (shop in shoppingRowList){
            total += (shop.price * shop.quantity)
        }
        return total
    }
}