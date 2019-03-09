package br.com.agendaodonto.database

import android.arch.persistence.room.Room
import android.content.Context

class MessageService() {

    object db {
        fun getDb(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "agendaodonto"
            ).allowMainThreadQueries().build()
        }
    }

}