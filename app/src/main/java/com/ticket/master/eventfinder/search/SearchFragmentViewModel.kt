package com.ticket.master.eventfinder.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ticket.master.eventfinder.services.AutoSuggestApi
import kotlinx.coroutines.launch

class SearchFragmentViewModel : ViewModel() {
    val stringList = MutableLiveData<List<String>>()
    fun getSuggestions(keyword: String) {
        viewModelScope.launch {
            try {
                val listString = AutoSuggestApi.retrofitService.getSuggestions(keyword)
                stringList.value = listString
            } catch (e: Exception) {

            }
        }
    }
}