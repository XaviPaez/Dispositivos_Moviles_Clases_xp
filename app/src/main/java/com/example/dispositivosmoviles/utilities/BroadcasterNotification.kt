package com.example.dispositivosmoviles.utilities
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.ui.activities.NotificationActivity

class BroadcasterNotification : BroadcastReceiver() {

    val CHANNEL : String = "Notificaciones"

    override fun onReceive(context: Context, intent: Intent) {
        val myIntent = Intent(context,NotificationActivity::class.java)
        val myPendingIntent = PendingIntent.getActivity(context,0,myIntent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        val noti = NotificationCompat.Builder(context, CHANNEL)
        noti.setContentTitle("Primera Notificacion")
        noti.setContentText("Tienes una notificacion")
        noti.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Esta es una notificacion para recordar que estamos trabajando en android"))
        noti.setSmallIcon(R.drawable.baseline_home_24)
        noti.setContentIntent(myPendingIntent)
        noti.setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, noti.build())

    }


}