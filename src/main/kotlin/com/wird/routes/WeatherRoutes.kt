package com.wird.routes

import com.wird.service.WeatherService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.weatherRoutes(weatherService: WeatherService) {
    route("/weather") {
        get("{location}") {
            val location = call.parameters["location"]
            if (location != null) {
                val weatherData = weatherService.getWeather(location)
                if (weatherData != null) {
                    val jsonResponse = Json.encodeToString(weatherData)
                    call.respondText(jsonResponse, ContentType.Application.Json)
                } else {
                    call.respond(HttpStatusCode.NotFound, "No data available for $location")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Location parameter missing")
            }
        }
    }
}
