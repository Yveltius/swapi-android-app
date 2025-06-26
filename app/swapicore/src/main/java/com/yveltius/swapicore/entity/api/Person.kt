package com.yveltius.swapicore.entity.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Person(
    val name: String,
    @SerialName("height") private val _height: String,
    @SerialName("mass") private val _mass: String,
    @SerialName("hair_color") val hairColor: String,
    @SerialName("skin_color") val skinColor: String,
    @SerialName("eye_color") val eyeColor: String,
    @SerialName("birth_year") val birthYear: String,
    val gender: String,
    @SerialName("homeworld") val homeWorldUrl: String,
    @SerialName("films") val filmUrls: List<String>,
    val species: List<String>,
    @SerialName("vehicles") val vehicleUrls: List<String>,
    @SerialName("starships") val starshipUrls: List<String>,
    val created: String,
    val edited: String,
    val url: String
) {
    @Transient
    val height = _height.toIntOrNull() ?: -1

    @Transient
    val mass = _mass.toIntOrNull() ?: -1

    @Transient
    val vehicleCount = vehicleUrls.size + starshipUrls.size
}