package br.com.agendaodonto.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "to") var to: String?,
    @ColumnInfo(name = "content") var content: String?,
    @ColumnInfo(name = "received_at") var receivedAt: String?,
    @ColumnInfo(name = "notified") var notified: Boolean?,
    @ColumnInfo(name = "schedule_id") var scheduleId: String?,
    @ColumnInfo(name = "server_notified") var serverNotified: Boolean?
)