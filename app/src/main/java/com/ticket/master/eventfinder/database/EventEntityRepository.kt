package com.ticket.master.eventfinder.database

import androidx.lifecycle.LiveData

class EventEntityRepository(val eventEntityDao: EventDao) {

    val favoritesEventList: LiveData<List<EventEntity>> = eventEntityDao.getEventLiveData()

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