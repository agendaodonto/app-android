package br.com.agendaodonto.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import br.com.agendaodonto.RetrofitConfig
import br.com.agendaodonto.database.MessageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateNotificationStatusService : Service() {
    val TAG = "UpdateNotificationStatusService"

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null ) {
            Log.d(TAG, "Intent not configured")
            throw Exception("Intent not configured")
        }
        val token = intent.extras!!.getString("token")
        val payload = UpdateScheduleData(1)
        val scheduleId = intent.extras!!.getString("scheduleId")
        val job = RetrofitConfig().getScheduleService().updateScheduleStatus(token, scheduleId, payload)
        val notificationEntity = MessageService.getInstance(this).messageDao().getByScheduleId(scheduleId.toInt())
        job.enqueue(object : Callback<ScheduleStatusResponse> {
            override fun onResponse(
                call: Call<ScheduleStatusResponse>,
                response: Response<ScheduleStatusResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Success when updating the server status")
                    notificationEntity.serverNotified = true
                    MessageService.getInstance(this@UpdateNotificationStatusService).messageDao()
                        .update(notificationEntity)
                    Toast.makeText(
                        this@UpdateNotificationStatusService,
                        "Sucesso !",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(TAG, "Failed to update the server (HTTP ${response.code()}")
                    Log.e(TAG, response.body().toString());
                    Toast.makeText(
                        this@UpdateNotificationStatusService,
                        "Falhou !",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ScheduleStatusResponse>, t: Throwable) {
                Log.e(TAG, "Failed to update the server", t)
                Toast.makeText(this@UpdateNotificationStatusService, "Falhou !", Toast.LENGTH_SHORT)
                    .show()
            }

        })
        return super.onStartCommand(intent, flags, startId)
    }
}
