package com.ticket.master.eventfinder.services

import com.ticket.master.eventfinder.models.event.EventItem
import com.ticket.master.eventfinder.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


interface EventItemService {
    @GET("events")
    suspend fun getEvents(
        @Query("radius") radius: Int,
        @Query("category") category: String,
        @Query("keyword") keyword: String,
        @Query("geoPoint") geoPoint: String
    ): List<EventItem>

    object EventsServiceApi {
        val retrofitService: EventItemService by lazy {
            retrofit.create(EventItemService::class.java)
        }
    }
}