package br.com.agendaodonto

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitConfig {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()


    fun getLoginService(): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    fun getScheduleService(): ScheduleService {
        return retrofit.create(ScheduleService::class.java)
    }

}