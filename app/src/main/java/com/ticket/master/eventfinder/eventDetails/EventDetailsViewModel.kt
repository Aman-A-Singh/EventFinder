package com.ticket.master.eventfinder.eventDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticket.master.eventfinder.models.event.EventDetails
import com.ticket.master.eventfinder.models.event.EventItem
import com.ticket.master.eventfinder.services.EventService
import com.ticket.master.eventfinder.util.UIState
import kotlinx.coroutines.launch

class EventDetailsViewModel:ViewModel() {
    lateinit var eventData : EventDetails
    val uiState : MutableLiveData<UIState> = MutableLiveData(UIState.INPROGREES)

    fun getEventData(event_id:String){
        viewModelScope.launch{
            val event = EventService.EventsServiceApi.retrofitService.getEventDetail(event_id)
            eventData = event
            uiState.postValue(UIState.COMPLETED)
        }
    }
}