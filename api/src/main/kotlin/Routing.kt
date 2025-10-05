package com.example.com

import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import chapter.helloRoutes

fun Application.configureRouting() {
    routing {
        route("/api") {
            helloRoutes()
        }

        get("/") {
            call.respondText("Hello World!")
        }
    }
}
