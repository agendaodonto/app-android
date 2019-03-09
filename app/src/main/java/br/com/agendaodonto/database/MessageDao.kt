package br.com.agendaodonto.database

import android.arch.persistence.room.*


@Dao
interface MessageDao {
    @Query("SELECT * FROM messageentity")
    fun getAll(): List<MessageEntity>

    @Query("SELECT * FROM messageentity WHERE schedule_id=:scheduleId")
    fun getByScheduleId(scheduleId: Int): MessageEntity

    @Insert
    fun insert(message: MessageEntity)

    @Update
    fun update(message: MessageEntity)

    @Delete
    fun delete(user: MessageEntity)
}