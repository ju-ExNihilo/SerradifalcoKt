package com.jgdeveloppement.pizza_serradifalco.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.jgdeveloppement.pizza_serradifalco.R

object Notification {

    private const val notificationID = 101
    private const val channelID = "com.jgdeveloppement.pizza-serradifalco"

    fun show(context: Context) {
        createNotificationChannel(context)
        val message = "Serradifalco vous remercie pour la commande passé.\n " +
                "Une email de confirmation vous a été envoyez\n " +
                "(penser à regarder dans vos spams)"
        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.pizza)
            .setContentTitle("Confirmation de Commande")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        with(NotificationManagerCompat.from(context)) {
            notify(notificationID, builder.build())
        }

    }

    private fun createNotificationChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "My Channel"
            val channelDescription = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelID, name, importance)
            channel.apply {
                description = channelDescription
            }

            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}