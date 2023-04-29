package com.ticket.master.eventfinder.models.event

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

data class EventItem(
    val _embedded: Embedded,
    val classifications: List<Classification>,
    val dates: Dates,
    val id: String,
    val images: List<Image>,
    val name: String,
    val type: String
)

data class Embedded(
    val venues: List<Venue>
)

data class Venue(
    val name: String
)

data class Classification(
    val segment: Segment
)

data class Segment(
    val name: String
)

data class Dates(
    val start: Start,
)

data class Start(
    private val localDate: String,
    private val localTime: String,
){
    val date : Date?
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            return try{
                dateFormat.parse(localDate)
            }catch (e:Exception){
                null
            }
        }
    val time :Date?
        get() {
            val dateFormat = SimpleDateFormat("HH:mm:ss")
            return try{
                dateFormat.parse(localTime)
            }catch (e:Exception){
                null
            }
        }
}

data class Image(
    val url: String,
    val alt: String? = null
)