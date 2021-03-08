package com.jgdeveloppement.pizza_serradifalco.networking

import java.io.IOException

object ConnexionInternet {
//    @get:Throws(InterruptedException::class, IOException::class)
//    val isConnected: Boolean
//        get() {
//            val command = "ping -c 1 google.com"
//            return Runtime.getRuntime().exec(command).waitFor() == 0
//        }

    fun isConnected(callback : (ok: Boolean)->Unit){
        val command = "ping -c 1 google.com"
        val ping = Runtime.getRuntime().exec(command).waitFor()
        if (ping == 0) callback(true) else callback(false)
    }
}