package com.wird.repository

import com.wird.domain.WeatherData
import com.wird.domain.WeatherValues
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import redis.clients.jedis.Jedis

class WeatherRepositoryImplTest {
    private val jedis = mockk<Jedis>(relaxed = true)
    private val repository = WeatherRepositoryImpl(jedis)

    @Test
    fun testSetWeatherStoresDataInRedis() {
        val weatherValues = WeatherValues(
            humidity = 70,
            temperature = 22.0,
            windSpeed = 5.0
        )
        val weatherData = WeatherData("2024-07-29T18:00:00Z", weatherValues)
        val expectedJson = Json.encodeToString(weatherData)

        every { jedis.set(any<String>(), any<String>()) } returns "OK"

        repository.setWeather("santiago", weatherData)

        verify { jedis.set("santiago", expectedJson) }
    }

    @Test
    fun testGetWeatherRetrievesDataFromRedis() {
        val weatherValues = WeatherValues(
            humidity = 70,
            temperature = 22.0,
            windSpeed = 5.0
        )
        val weatherData = WeatherData("2024-07-29T18:00:00Z", weatherValues)
        val weatherDataJson = Json.encodeToString(weatherData)
        every { jedis.get("Santiago") } returns weatherDataJson

        val result = repository.getWeather("Santiago")

        assertNotNull(result)
        assertEquals(weatherData.time, result?.time)
        assertEquals(weatherData.values.temperature, result?.values?.temperature)
    }

    @Test
    fun testLogErrorStoresErrorMessageInRedis() {
        every { jedis.set(any<String>(), any<String>()) } returns "OK"

        val timestamp = System.currentTimeMillis()
        repository.logError(timestamp, "Test error")

        verify { jedis.set("log_$timestamp", "Test error") }
    }
}
