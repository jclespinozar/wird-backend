package com.wird

import com.typesafe.config.ConfigFactory
import com.wird.repository.WeatherRepository
import com.wird.routes.weatherRoutes
import com.wird.service.WeatherService
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.routing.*
import redis.clients.jedis.Jedis

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CallLogging)

    val config = ConfigFactory.load()
    val jedis = Jedis(config.getString("redis.host"), config.getInt("redis.port"))
    val weatherRepository = WeatherRepository(jedis)
    val weatherService = WeatherService(weatherRepository, config)

    routing {
        weatherRoutes(weatherService)
    }

    weatherService.startWeatherUpdateTimer()
}
