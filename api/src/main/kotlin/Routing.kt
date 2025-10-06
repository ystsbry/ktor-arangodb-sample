package com.example.com

import io.github.smiley4.ktoropenapi.get
import io.ktor.server.response.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import chapter.hello2Message
import chapter.hello.HelloRoutes

fun Application.configureRouting() {
    routing {
        route("chapter") { 
            HelloRoutes()
        }

        get("/") {
            call.respondText("Hello World!")
        }

        get("hello2", {
            description = "Hello world sample"
            response {
                HttpStatusCode.OK to {
                    description = "plain text"
                    body<String>()
                }
            }
        }) {
            call.respondText(hello2Message())
        }
    }
}
