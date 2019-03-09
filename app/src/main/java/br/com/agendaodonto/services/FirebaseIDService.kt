package br.com.agendaodonto.services

import android.telephony.SmsManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast
import br.com.agendaodonto.Preferences
import br.com.agendaodonto.RetrofitConfig
import br.com.agendaodonto.database.MessageEntity
import br.com.agendaodonto.database.MessageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirebaseIDService : FirebaseMessagingService() {
    val TAG = "FirebaseService"

    @Override
    override fun onNewToken(newToken: String) {
        System.out.println("New Token => $newToken")
    }

    @Override
    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "New message received!")
        val entity = MessageEntity(
            uid = 0,
            to = message.data["sendTo"],
            content = message.data["content"],
            scheduleId = message.data["scheduleId"],
            notified = false
        )
        MessageService.db.getDb(this).messageDao().insertAll(entity)
        sendTextMessage(message.data["sendTo"]!!, message.data["content"]!!, message.data["scheduleId"]!!)
    }

    private fun sendTextMessage(to: String, body: String, scheduleId: String) {
        val smsManager = SmsManager.getDefault()
        val parts = smsManager.divideMessage(body)
        val sentIntents = ArrayList<PendingIntent>()
        val mSendIntent = Intent("br.com.agendaodonto.ACTION_MESSAGE_SENT")

        for (i in 0 until parts.size) {
            mSendIntent.putExtra("scheduleId", scheduleId)
            sentIntents.add(PendingIntent.getBroadcast(this, 0, mSendIntent, 0))
        }

        smsManager.sendMultipartTextMessage(to, null, parts, sentIntents, null)

        val messageSentReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val scheduleId = intent.getStringExtra("scheduleId")
                val token = "Token " + Preferences(context).token
                val payload = UpdateScheduleData(1)
                val job = RetrofitConfig().getScheduleService().updateScheduleStatus(token, scheduleId, payload)

                job.enqueue(object : Callback<ScheduleStatusResponse> {
                    override fun onResponse(
                        call: Call<ScheduleStatusResponse>,
                        response: Response<ScheduleStatusResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Sucesso !", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Falhou !", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ScheduleStatusResponse>, t: Throwable) {
                        Toast.makeText(context, "Falhou !", Toast.LENGTH_SHORT).show()
                    }

                })
                Log.d(TAG, "Message was sent !!")
            }
        }

        registerReceiver(messageSentReceiver, IntentFilter("br.com.agendaodonto.ACTION_MESSAGE_SENT"))
    }
}