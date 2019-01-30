package br.com.agendaodonto

import com.fasterxml.jackson.annotation.JsonProperty
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface LoginService {

    @POST("auth/login/")
    fun authenticate(@Body email: LoginData): Call<LoginResposeData>

    @PATCH("v1/dentists/me/")
    fun updateDeviceId(@Header("Authorization") token: String, @Body tokenData: TokenData): Call<DentistData>
}


class LoginData(val email: String, val password: String) {
}

class TokenData(val device_token: String) {
}

class DentistData {
    @JsonProperty("id")
    private lateinit var id: Number

    @JsonProperty("first_name")
    private lateinit var firstName: String

    @JsonProperty("last_name")
    private lateinit var lastName: String

    @JsonProperty("email")
    private lateinit var email: String

    @JsonProperty("cro")
    private lateinit var cro: String

    @JsonProperty("sex")
    private lateinit var sex: String

    @JsonProperty("cro_state")
    private lateinit var croState: String

    @JsonProperty("device_token")
    private lateinit var deviceToken: String
}