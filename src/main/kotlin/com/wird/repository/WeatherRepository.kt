package com.wird.repository

import com.wird.domain.WeatherData
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import redis.clients.jedis.Jedis

class WeatherRepository(private val jedis: Jedis) {

    fun getWeather(location: String): WeatherData? {
        val data = jedis[location] ?: return null
        return Json.decodeFromString<WeatherData>(data)
    }

    fun setWeather(location: String, data: WeatherData) {
        val jsonData = Json.encodeToString(data)
        jedis[location] = jsonData
    }

    fun logError(timestamp: Long, message: String) {
        jedis["log_$timestamp"] = message
    }
}
