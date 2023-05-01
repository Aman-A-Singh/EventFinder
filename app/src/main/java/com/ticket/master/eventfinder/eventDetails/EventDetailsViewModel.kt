package com.ticket.master.eventfinder.eventDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticket.master.eventfinder.models.artist.ArtistDetail
import com.ticket.master.eventfinder.models.event.EventDetails
import com.ticket.master.eventfinder.models.event.EventItem
import com.ticket.master.eventfinder.services.ArtistDetailService
import com.ticket.master.eventfinder.services.ArtistDetailServiceApi
import com.ticket.master.eventfinder.services.EventService
import com.ticket.master.eventfinder.util.UIState
import kotlinx.coroutines.launch

class EventDetailsViewModel : ViewModel() {
    lateinit var eventData: EventDetails
    var artistList: MutableLiveData<List<ArtistDetail>> = MutableLiveData()
    val uiState: MutableLiveData<UIState> = MutableLiveData(UIState.INPROGREES)

    fun getEventData(event_id: String) {
        viewModelScope.launch {
            val event = EventService.EventsServiceApi.retrofitService.getEventDetail(event_id)
            eventData = event
            uiState.postValue(UIState.COMPLETED)
        }
    }

    fun getArtists(performers: List<String>) {
        viewModelScope.launch {
            val _artistList = ArtistDetailServiceApi.retrofitService.getEventList(performers)
            artistList.postValue(_artistList)
        }
    }
}