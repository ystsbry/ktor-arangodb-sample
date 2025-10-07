package common

import io.github.smiley4.ktoropenapi.resources.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import io.ktor.resources.Resource


@Serializable
@Resource("collection")
data class Collection(val name: String)

fun Route.commonRoutes() {
    route("common") {
        post<Collection>({
            request {
                queryParameter<String>("name") {
                    description = "コレクション名"
                    required = true
                }
            }
            response {
                HttpStatusCode.OK to { body<String>() }
            }
        }) { req ->
            call.respond(HttpStatusCode.OK, "Collection: ${req.name}")
        }
    }
}