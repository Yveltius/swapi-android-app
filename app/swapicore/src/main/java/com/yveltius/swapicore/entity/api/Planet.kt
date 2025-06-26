package com.yveltius.swapicore.entity.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Planet(
    val name: String,
    @SerialName("rotation_period") private val _rotationPeriod: String,
    @SerialName("orbital_period") private val _orbitalPeriod: String,
    @SerialName("diameter") private val _diameter: String,
    val climate: String,
    val gravity: String,
    val terrain: String,
    @SerialName("surface_water") private val _surfaceWater: String,
    @SerialName("population") private val _population: String,
    @SerialName("residents") val residentUrls: List<String>,
    @SerialName("films") val filmUrls: List<String>,
    val created: String,
    val edited: String,
    val url: String
)