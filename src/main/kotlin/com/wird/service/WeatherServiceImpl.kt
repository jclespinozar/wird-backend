package com.wird.service

import com.typesafe.config.Config
import com.wird.domain.WeatherData
import com.wird.repository.WeatherRepository
import io.github.resilience4j.kotlin.retry.executeSuspendFunction
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.*
import kotlin.random.Random


class WeatherServiceImpl(private val weatherRepository: WeatherRepository, private val config: Config) : WeatherService{

    private val logger = LoggerFactory.getLogger(WeatherServiceImpl::class.java)
    private val locations = listOf(
        "santiago" to "CL",
        "zurich" to "CH",
        "auckland" to "NZ",
        "sidney" to "AU",
        "london" to "UK",
        "georgia" to "USA"
    )
    private val apiKey = config.getString("tomorrowApi.key")
    private val apiUrl = config.getString("tomorrowApi.url")
    private val client = HttpClient()

    private val retryConfig = RetryConfig.custom<RetryConfig>()
        .maxAttempts(3)
        .waitDuration(Duration.ofMillis(2000))
        .retryExceptions(RuntimeException::class.java)
        .build()
    private val retry = Retry.of("weatherApiRetry", retryConfig)

    override fun startWeatherUpdateTimer() {
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                locations.forEach { (location, countryCode) ->
                    runBlocking {
                        try {
                            retry.executeSuspendFunction {
                                if (Random.nextDouble() < 0.2) {
                                    logger.error("Simulated API failure for $location, $countryCode")
                                    throw RuntimeException("The API Request Failed")
                                }
                                logger.info("Fetching weather data for $location, $countryCode")
                                val response: HttpResponse = client.get("$apiUrl?location=$countryCode") {
                                    headers {
                                        append("apikey", apiKey)
                                    }
                                }
                                val responseData = response.bodyAsText()
                                val jsonObject = Json.parseToJsonElement(responseData).jsonObject
                                val dataElement = jsonObject["data"].toString()
                                val weatherData = Json.decodeFromString(WeatherData.serializer(), dataElement)
                                weatherRepository.setWeather(location, weatherData)
                                logger.info("Successfully fetched weather data for $location")
                            }
                        } catch (e: Exception) {
                            logger.error("Failed to fetch weather data for $location: ${e.message}")
                            weatherRepository.logError(System.currentTimeMillis(), e.message ?: "Unknown error")
                        }
                    }
                }
            }
        }, 0, 300000) // 5 minutes
    }

    override fun getWeather(location: String): WeatherData? {
        return weatherRepository.getWeather(location)
    }
}
