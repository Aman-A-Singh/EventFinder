package com.ticket.master.eventfinder.models.artist

data class ArtistDetail(
    val albums: List<Album>,
    val external_urls: ExternalUrlsXX,
    val followers: Followers,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Int
)
data class Album(
    val images: List<Image>
)
data class Image(
    val height: Int,
    val url: String,
    val width: Int
)

data class ExternalUrlsXX(
    val spotify: String
)

data class Followers(
    val total: Int
)