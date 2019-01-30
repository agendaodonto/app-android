package br.com.agendaodonto

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    val PREFS_FILENAME = "br.com.agendaodonto.prefs"
    val TOKEN = "token"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var token: String
        get() = prefs.getString(TOKEN, "")!!
        set(value) = prefs.edit().putString(TOKEN, value).apply()


}