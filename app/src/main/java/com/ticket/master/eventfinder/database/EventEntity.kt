package com.ticket.master.eventfinder.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ticket.master.eventfinder.util.Constants.EVENT_TABLE_NAME

@Entity(tableName = EVENT_TABLE_NAME)
data class EventEntity(
    @PrimaryKey val id: String
)