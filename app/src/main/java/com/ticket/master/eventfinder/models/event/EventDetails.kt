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
    data class Classification(
        val segment: Segment?,
        val genre: Segment?,
        val subGenre: Segment?,
        val type: Segment?,
        val subType: Segment?
    )
    data class Embedded(
        val venues: List<Venues>,
        val attractions : List<Attractions>?
    ){
        data class Venues(
            val address: Address?,
            val city: City?,
            val country: Country?,
            val location: Location?,
            val name: String,
            val postalCode: String?,
            val state: State?,
            val url: String?,
            val boxOfficeInfo :BoxOfficeInfo?,
            val generalInfo : GeneralInfo?
        ){
            data class Address(
                val line1: String
            )

            data class City(
                val name: String
            )

            data class Country(
                val countryCode: String,
                val name: String
            )

            data class Location(val longitude:String,val latitude : String)

            data class State(
                val name: String,
                val stateCode: String
            )
        }
    }

    data class Attractions(val url: String,val name:String)

    data class Dates(
        val start: Start,
        val status: Status
    ) {
        data class Status(val code: String)
    }
    data class BoxOfficeInfo(
        val openHoursDetail: String?,
        val phoneNumberDetail: String?
    )
    data class GeneralInfo(
        val childRule: String?,
        val generalRule: String?
    )
}

data class SeatMap(val staticUrl: String)

data class PriceRangesItem(
    val currency: String,
    val max: Double,
    val min: Double,
    val type: String
)