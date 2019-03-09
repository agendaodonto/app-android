package br.com.agendaodonto.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "to") var to: String?,
    @ColumnInfo(name = "content") var content: String?,
    @ColumnInfo(name = "notified") var notified: Boolean?,
    @ColumnInfo(name = "schedule_id") var scheduleId: String?
)