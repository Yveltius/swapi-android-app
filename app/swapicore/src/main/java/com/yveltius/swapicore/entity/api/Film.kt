package com.yveltius.swapicore.entity.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Film(
    val title: String,
    @SerialName("episode_id") val episodeId: Int,
    @SerialName("opening_crawl") val openingCrawl: String,
    val director: String,
    val producer: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("characters") val characterUrls: List<String>,
    @SerialName("planets") val planetUrls: List<String>,
    @SerialName("startships") val starshipUrls: List<String>,
    @SerialName("vehicles") val vehicleUrls: List<String>,
    @SerialName("species") val speciesUrls: List<String>,
    val created: String,
    val edited: String,
    val url: String
)