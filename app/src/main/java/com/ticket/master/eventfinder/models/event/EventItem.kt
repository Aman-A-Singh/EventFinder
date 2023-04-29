package com.ticket.master.eventfinder.models.event

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
    val localDate: String,
    val localTime: String,
)

data class Image(
    val url: String,
    val alt: String? = null
)