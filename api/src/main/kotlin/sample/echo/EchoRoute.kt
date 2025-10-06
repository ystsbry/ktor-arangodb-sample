package sample.echo

import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class EchoReq(val msg: String)

@Serializable
data class EchoRes(val echoed: String)

fun Route.EchoRoutes() {
    route("echo") {
        post({
            description = "メッセージをエコーする"
            request { 
                body<EchoReq>() {
                    required = true
                }
            }
            response { 
                HttpStatusCode.OK to { body<EchoRes>() }
                HttpStatusCode.BadRequest to { body<String>() }
            }
        }) {
            val req = runCatching { call.receive<EchoReq>() }
                .getOrElse { e ->
                    application.log.warn("echo body parse failed", e)
                    return@post call.respond(HttpStatusCode.BadRequest, "Invalid JSON body") 
                }

            var resp = EchoService.getEchoMessage(req.msg)
            call.respond(EchoRes(resp))
        }
    }
}