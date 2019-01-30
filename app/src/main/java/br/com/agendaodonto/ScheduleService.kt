package br.com.agendaodonto

import com.fasterxml.jackson.annotation.JsonProperty
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ScheduleService {
    @POST("v1/schedules/{schedule_id}/notification/")
    fun updateScheduleStatus(
        @Header("Authorization") token: String,
        @Path("schedule_id") scheduleId: String,
        @Body data: UpdateScheduleData
    ): Call<ScheduleStatusResponse>
}

class UpdateScheduleData(val new_status: Number) {

}

class ScheduleStatusResponse {
    @JsonProperty("success")
    private var success: Boolean? = null
}