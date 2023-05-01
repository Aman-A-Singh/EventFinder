package com.ticket.master.eventfinder.services

import com.ticket.master.eventfinder.models.artist.ArtistDetail
import com.ticket.master.eventfinder.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ArtistDetailService {
    @GET("/api/artist-detail")
    suspend fun getEventList(
        @Query("performers")performers:List<String>
    ): List<ArtistDetail>
}

object ArtistDetailServiceApi {
    val retrofitService: ArtistDetailService by lazy {
        retrofit.create(ArtistDetailService::class.java)
    }
}