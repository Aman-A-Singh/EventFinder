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
            }catch (e:Exception){

            }
        }
    }

    fun getLocation(address: String?) {
        if(address != null){
            viewModelScope.launch {
                val _location = LocationServiceApi.retrofitService.getLocation(LOCATION_KEY, address)
                location.postValue(_location.results.get(0).geometry.location)
            }
        }else{
            //write the logic to get last known location of user and post the location in LiveData
        }
    }
}