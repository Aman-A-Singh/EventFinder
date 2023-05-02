package com.ticket.master.eventfinder.models.location

data class IpInfo(
    val city: String,
    val country: String,
    val ip: String,
    val loc: String,
    val org: String,
    val postal: String,
    val region: String,
    val timezone: String
)