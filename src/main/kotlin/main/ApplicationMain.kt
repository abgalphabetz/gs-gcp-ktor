package main

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import java.net.Inet6Address
import java.time.ZonedDateTime

fun Application.main() {
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            registerModule(JavaTimeModule())
        }
    }
    routing {
        get("/_ah/health") {
            call.respondText("", ContentType.Text.Html)
        }
        get("/") {
            call.respond(mapOf<String, String>(
                    "womai" to Inet6Address.getLocalHost().hostName,
                    "timestamp" to ZonedDateTime.now().toString()
            ))
        }
        get("/echo/{message?}") {
            val message = call.parameters["message"]
            val warning = message?.let { "" } ?: "Please input any message!"
            call.respond(mapOf(
                    "message" to (message ?: ""),
                    "warning" to warning,
                    "timestamp" to ZonedDateTime.now().toString()
            ))
        }
    }
}