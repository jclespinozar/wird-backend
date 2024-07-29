package com.wird.repository

import com.wird.domain.WeatherData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import redis.clients.jedis.Jedis

class WeatherRepositoryImpl(private val jedis: Jedis) : WeatherRepository {

    override fun getWeather(location: String): WeatherData? {
        val data = jedis[location] ?: return null
        return Json.decodeFromString<WeatherData>(data)
    }

    override fun setWeather(location: String, data: WeatherData) {
        val jsonData = Json.encodeToString(data)
        jedis[location] = jsonData
    }

    override fun logError(timestamp: Long, message: String) {
        jedis["log_$timestamp"] = message
    }
}
