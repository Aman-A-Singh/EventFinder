package com.ticket.master.eventfinder.models

import java.net.URL

data class EventData(
    val id : String,
    val eventName: String,
    val eventDate: String,
    val eventLocation: String,
    val eventTime: String,
    val eventType: String,
    val eventImage : String
) {

}
