package com.ticket.master.eventfinder.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticket.master.eventfinder.models.event.EventItem
import com.ticket.master.eventfinder.models.location.LocationX
import com.ticket.master.eventfinder.services.EventService
import com.ticket.master.eventfinder.services.LocationServiceApi
import com.ticket.master.eventfinder.util.Constants
import com.ticket.master.eventfinder.util.UIState
import kotlinx.coroutines.launch

class SearchResultFragmentViewModel : ViewModel() {
    val eventList: MutableLiveData<List<EventItem>> = MutableLiveData()
    val location = MutableLiveData<LocationX>()
    val uiState: MutableLiveData<UIState> = MutableLiveData(UIState.INPROGREES)

    fun getEvents(radius: Int, category: String, keyword: String, geoHash: String) {
        viewModelScope.launch {
            try {
                eventList.value = EventService.EventsServiceApi.retrofitService.getEventList(
                    radius,
                    category,
                    keyword,
                    geoHash
                )
                uiState.postValue(UIState.COMPLETED)
            } catch (e: Exception) {
                uiState.postValue(UIState.ERROR)
            }
        }
    }

    fun getLocation(address: String?) {
        if (address != null) {
            viewModelScope.launch {
                try {
                    val _location = LocationServiceApi.retrofitService1.getLocation(
                        Constants.LOCATION_KEY,
                        address
                    )
                    location.postValue(_location.results.get(0).geometry.location)
                } catch (e: Exception) {
                    uiState.postValue(UIState.ERROR)
                }
            }
        } else {
            viewModelScope.launch {
                val ipInfo =
                    LocationServiceApi.retrofitService2.getAutoLocation(Constants.AUTO_LOCATION_IP_KEY)
                val splitString = ipInfo.loc.split(",")
                val _location = LocationX(splitString[0].toDouble(), splitString[1].toDouble())
                location.postValue(_location)
            }
        }
    }
}