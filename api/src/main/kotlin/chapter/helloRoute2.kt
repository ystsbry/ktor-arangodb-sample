package chapter

import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.helloRoutes() {
    get("/hello2") {
        call.respondText("hello world2!!")
    }
}