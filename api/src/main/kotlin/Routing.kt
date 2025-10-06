package com.example.com

import io.github.smiley4.ktoropenapi.get
import io.ktor.server.response.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import sample.hello.HelloRoutes
import sample.echo.EchoRoutes

fun Application.configureRouting() {
    routing {
        route("sample") { 
            HelloRoutes()
            EchoRoutes()
        }

        get("/") {
            call.respondText("Hello World!")
        }
    }
}
