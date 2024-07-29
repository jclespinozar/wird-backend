package com.wird.repository

import com.wird.domain.WeatherData

interface WeatherRepository {
    fun setWeather(location: String, weatherData: WeatherData)
    fun getWeather(location: String): WeatherData?
    fun logError(timestamp: Long, message: String)
}
