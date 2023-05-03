package com.ticket.master.eventfinder.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ticket.master.eventfinder.util.Constants.EVENT_TABLE_NAME

@Dao
interface EventDao {

    @Query("SELECT * FROM $EVENT_TABLE_NAME")
    suspend fun getEventList(): List<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Delete
    suspend fun removeEvent(event: EventEntity)
}