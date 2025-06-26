package com.yveltius.swapicore.entity.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Species(
    val name: String,
    val classification: String,
    val designation: String,
    @SerialName("average_height") private val _averageHeight: String,
    // string containing comma separated strings, ex "caucasian, black" etc. can also be "n/a"
    @SerialName("skin_colors") private val _skinColors: String,
    @SerialName("hair_colors") private val _hairColors: String,
    @SerialName("eye_colors") private val _eyeColors: String,
    @SerialName("average_lifespan") private val _lifespan: String,
    @SerialName("homeworld") val homeworldUrl: String?,
    val language: String,
    @SerialName("people") val peopleUrls: List<String>,
    @SerialName("films") val filmUrls: List<String>,
    val created: String,
    val edited: String,
    val url: String
)