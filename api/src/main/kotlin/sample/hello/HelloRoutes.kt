package sample.hello

import io.github.smiley4.ktoropenapi.get
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import sample.hello.HelloService

fun Route.HelloRoutes() {
    route("hello") { 
        get({
            description = "Hello world sample"
            response { HttpStatusCode.OK to { body<String>() } }
        }) {
            call.respondText(HelloService.getHelloMessage())
        }
    }
}

