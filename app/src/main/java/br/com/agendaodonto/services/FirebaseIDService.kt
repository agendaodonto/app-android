package br.com.agendaodonto.services

import android.app.PendingIntent
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import br.com.agendaodonto.Preferences
import br.com.agendaodonto.database.MessageEntity
import br.com.agendaodonto.database.MessageService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*


class FirebaseIDService : FirebaseMessagingService() {
    val TAG = "FirebaseService"


    @Override
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "New message received!")
        val entity = MessageEntity(
            uid = 0,
            to = message.data["sendTo"],
            receivedAt = Date().toString(),
            content = message.data["content"],
            scheduleId = message.data["scheduleId"],
            notified = false,
            serverNotified = false
        )
        MessageService.getInstance(this).messageDao().insert(entity)
        sendTextMessage(message.data["sendTo"]!!, message.data["content"]!!, message.data["scheduleId"]!!)
    }

    private fun sendTextMessage(to: String, body: String, scheduleId: String) {
        Log.d(TAG, "Sending message to $to, scheduleId => $scheduleId")
        val smsManager = SmsManager.getDefault()
        val parts = smsManager.divideMessage(body)
        val sentIntents = ArrayList<PendingIntent>()
        val mSendIntent = Intent(this, MessageSentReceiver::class.java)
        val token = "Token " + Preferences(this).token

        mSendIntent.putExtra("token", token)
        mSendIntent.putExtra("scheduleId", scheduleId)

        for (i in 0 until parts.size) {
            sentIntents.add(PendingIntent.getBroadcast(this, 0, mSendIntent, 0))
        }

        smsManager.sendMultipartTextMessage(to, null, parts, sentIntents, null)

    }

}