package com.ticket.master.eventfinder.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ticket.master.eventfinder.util.Constants.EVENT_TABLE_NAME

@Entity(tableName = EVENT_TABLE_NAME)
data class EventEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "venue") val venue: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "url") val url: String
)