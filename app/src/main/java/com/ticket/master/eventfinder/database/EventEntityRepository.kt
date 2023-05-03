package com.ticket.master.eventfinder.database

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventEntityRepository(val eventEntityDao: EventDao) {

    suspend fun insert(event: EventEntity) {
        eventEntityDao.insertEvent(event)
    }

    suspend fun removeEvent(event: EventEntity) {
        eventEntityDao.removeEvent(event)
    }

    suspend fun getEventList():List<EventEntity> {
        return  eventEntityDao.getEventList()
    }
}