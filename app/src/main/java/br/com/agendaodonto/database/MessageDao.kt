package br.com.agendaodonto.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


@Dao
interface MessageDao {
    @Query("SELECT * FROM messageentity")
    fun getAll(): List<MessageEntity>

    @Insert
    fun insertAll(users: MessageEntity)

    @Delete
    fun delete(user: MessageEntity)
}