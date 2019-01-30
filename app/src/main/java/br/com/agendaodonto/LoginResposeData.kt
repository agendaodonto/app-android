package br.com.agendaodonto

import com.fasterxml.jackson.annotation.JsonProperty

class LoginResposeData {

    @JsonProperty("auth_token")
    private lateinit var authToken: String

    @JsonProperty("non_field_errors", required = false)
    private lateinit var nonFieldErrors: List<String>;

    fun getAuthToken(): String {
        return authToken
    }

    fun setAuthToken(token: String) {
        this.authToken = token;
    }
}