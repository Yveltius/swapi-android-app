package com.yveltius.swapicore.entity.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Vehicle(
    val name: String,
    val model: String,
    val manufacturer: String,
    @SerialName("cost_in_credits") private val _costInCredits: String,
    @SerialName("length") private val _length: String,
    @SerialName("max_atmosphering_speed") private val _maxAtmospheringSpeed: String,
    @SerialName("crew") private val _crew: String,
    @SerialName("passengers") private val _passengers: String,
    @SerialName("cargo_capacity") private val _cargoCapacity: String,
    val consumables: String,
    @SerialName("vehicle_class") val vehicleClass: String,
    @SerialName("pilots") val pilotUrls: List<String>,
    @SerialName("films") val filmUrls: List<String>,
    val created: String,
    val edited: String,
    val url: String
)