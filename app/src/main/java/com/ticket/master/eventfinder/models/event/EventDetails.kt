package com.ticket.master.eventfinder.models.event

data class EventDetails(
    val _embedded: Embedded,
    val classifications: List<Classification>,
    val dates: Dates,
    val id: String,
    val images: List<Image>,
    val name: String,
    val type: String,
    val seatmap: SeatMap,
    val url: String?,
    val priceRanges : ArrayList<PriceRangesItem> ?
) {
    data class Embedded(
        val venues: List<Venue>,
        val attractions : List<Attractions>
    )

    data class Attractions(val url: String)

    data class Dates(
        val start: Start,
        val status: Status
    ) {
        data class Status(val code: String)
    }
}

data class SeatMap(val staticUrl: String)

data class PriceRangesItem(
    val currency: String,
    val max: Double,
    val min: Double,
    val type: String
)