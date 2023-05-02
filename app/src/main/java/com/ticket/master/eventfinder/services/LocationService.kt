package com.ticket.master.eventfinder.services

import com.ticket.master.eventfinder.models.location.IpInfo
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

private val retrofit2 = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(Constants.AUTO_LOCATION_URL)
    .build()

interface LocationService {
    @GET("json")
    suspend fun getLocation(@Query("key") key: String, @Query("address") address: String): Location
    @GET("json")
    suspend fun getAutoLocation(@Query("token") token: String): IpInfo

}

object LocationServiceApi{
    val retrofitService1: LocationService by lazy {
        retrofit1.create(LocationService::class.java)
    }
    val retrofitService2: LocationService by lazy {
        retrofit2.create(LocationService::class.java)
    }
}