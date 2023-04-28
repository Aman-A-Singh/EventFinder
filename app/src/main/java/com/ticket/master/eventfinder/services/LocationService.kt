package com.ticket.master.eventfinder.services

import com.ticket.master.eventfinder.models.location.Location
import com.ticket.master.eventfinder.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit1 = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(Constants.LOCATION_URL)
    .build()

interface LocationService {
    @GET("json")
    suspend fun getLocation(@Query("key") key: String, @Query("address") address: String): Location

}

object LocationServiceApi{
    val retrofitService1: LocationService by lazy {
        retrofit1.create(LocationService::class.java)
    }

}