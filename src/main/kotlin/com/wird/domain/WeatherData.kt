package com.wird.domain

import kotlinx.serialization.Serializable

@Serializable
data class WeatherValues(
    val cloudBase: Double? = null,
    val cloudCeiling: Double? = null,
    val cloudCover: Int? = null,
    val dewPoint: Double? = null,
    val freezingRainIntensity: Int? = null,
    val humidity: Int,
    val precipitationProbability: Int? = null,
    val pressureSurfaceLevel: Double? = null,
    val rainIntensity: Int? = null,
    val sleetIntensity: Int? = null,
    val snowIntensity: Int? = null,
    val temperature: Double,
    val temperatureApparent: Double? = null,
    val uvHealthConcern: Int? = null,
    val uvIndex: Int? = null,
    val visibility: Double? = null,
    val weatherCode: Int? = null,
    val windDirection: Double? = null,
    val windGust: Double? = null,
    val windSpeed: Double
)

@Serializable
data class WeatherData(
    val time: String,
    val values: WeatherValues
)
