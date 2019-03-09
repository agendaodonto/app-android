package br.com.agendaodonto.database

import android.arch.persistence.room.Room
import android.content.Context

class MessageService() {


    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "agendaodonto"
        ).allowMainThreadQueries().build()
    }


}