package com.wird.service

import com.wird.domain.WeatherData

interface WeatherService {
    fun startWeatherUpdateTimer()
    fun getWeather(location: String): WeatherData?
}
