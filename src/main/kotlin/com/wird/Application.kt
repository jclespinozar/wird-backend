package com.wird

import com.typesafe.config.ConfigFactory
import com.wird.repository.WeatherRepositoryImpl
import com.wird.routes.weatherRoutes
import com.wird.service.WeatherServiceImpl
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.swagger.*
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
    val weatherRepositoryImpl = WeatherRepositoryImpl(jedis)
    val weatherServiceImpl = WeatherServiceImpl(weatherRepositoryImpl, config)

    routing {
        swaggerUI(path = "swagger", swaggerFile = "wird-backend-openapi.yaml")
        weatherRoutes(weatherServiceImpl)
    }

    weatherServiceImpl.startWeatherUpdateTimer()
}
