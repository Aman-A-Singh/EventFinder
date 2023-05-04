package com.ticket.master.eventfinder.home.favriotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ticket.master.eventfinder.database.EventEntity
import com.ticket.master.eventfinder.database.EventEntityDatabase
import com.ticket.master.eventfinder.database.EventEntityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val eventRepository: EventEntityRepository
    val favoritesEventList: LiveData<List<EventEntity>>
    init {
        val db = EventEntityDatabase.getInstance(application)
        val dao = db.eventEntityDao()
        eventRepository = EventEntityRepository(dao)
        favoritesEventList = eventRepository.favoritesEventList
    }

    fun removeEvent(event: EventEntity) =
        viewModelScope.launch(Dispatchers.IO) { eventRepository.removeEvent(event) }
}