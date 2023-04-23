package com.ticket.master.eventfinder.services

import com.ticket.master.eventfinder.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AutoSuggestService {
    @GET("/suggestions")
    suspend fun getSuggestions(@Query("keyword") keyword:String): List<String>
}

object AutoSuggestApi {
    val retrofitService: AutoSuggestService by lazy {
        retrofit.create(AutoSuggestService::class.java)
    }
}