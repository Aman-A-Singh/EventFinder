package com.ticket.master.eventfinder.services

import com.ticket.master.eventfinder.models.event.EventDetails
import com.ticket.master.eventfinder.models.event.EventItem
import com.ticket.master.eventfinder.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


interface EventService {
    @GET("events")
    suspend fun getEventList(
        @Query("radius") radius: Int,
        @Query("category") category: String,
        @Query("keyword") keyword: String,
        @Query("geoPoint") geoPoint: String
    ): List<EventItem>

    @GET("event/{event_id}")
    suspend fun getEventDetail(@Path("event_id") event_id : String) : EventDetails

    object EventsServiceApi {
        val retrofitService: EventService by lazy {
            retrofit.create(EventService::class.java)
        }
    }
}