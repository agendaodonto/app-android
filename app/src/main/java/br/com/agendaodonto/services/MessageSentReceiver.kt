package br.com.agendaodonto.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MessageSentReceiver : BroadcastReceiver() {
    val TAG = "MessageSentReceiver"
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received")

        val serviceIntent = Intent(context, UpdateNotificationStatusService::class.java)
        serviceIntent.putExtra("token", intent.extras.getString("token"))
        serviceIntent.putExtra("scheduleId", intent.extras.getString("scheduleId"))
        context.startService(serviceIntent)
    }
}
