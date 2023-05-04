package com.ticket.master.eventfinder.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticket.master.eventfinder.models.location.LocationX
import com.ticket.master.eventfinder.services.AutoSuggestApi
import com.ticket.master.eventfinder.services.LocationServiceApi
import com.ticket.master.eventfinder.util.Constants.AUTO_LOCATION_IP_KEY
import com.ticket.master.eventfinder.util.Constants.LOCATION_KEY
import kotlinx.coroutines.launch
import org.json.JSONObject

class SearchFragmentViewModel : ViewModel() {
    val stringList = MutableLiveData<List<String>>()
    val location = MutableLiveData<LocationX>()
    fun getSuggestions(keyword: String) {
        viewModelScope.launch {
            try {
                val listString = AutoSuggestApi.retrofitService.getSuggestions(keyword)
                stringList.value = listString
            } catch (e: Exception) {

            }
        }
    }

    fun getLocation(address: String) {
        viewModelScope.launch {
            val _location = LocationServiceApi.retrofitService1.getLocation(LOCATION_KEY, address)
            location.postValue(_location.results.get(0).geometry.location)
        }
    }

    fun getAutoLocation() {
        viewModelScope.launch {
            val ipInfo = LocationServiceApi.retrofitService2.getAutoLocation(AUTO_LOCATION_IP_KEY)
            val splitString = ipInfo.loc.split(",")
            val _location = LocationX(splitString[0].toDouble(), splitString[1].toDouble())
            location.postValue(_location)
        }
    }
}