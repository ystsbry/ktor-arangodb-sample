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
@Resource("common")
class Common {
    @Serializable
    @Resource("collection")
    data class Collection(
        val parent: Common = Common(),    
        val name: String
    )
}

fun Route.commonRoutes() {
    post<Common.Collection>({
        request {
            queryParameter<String>("name") {
                description = "connection name"
                required = true
            }
        }
        response {
            HttpStatusCode.OK to { body<String>() }
        }
    }) { req ->
        CommonService.createCollectionIfAbsent(req.name)
        call.respond(HttpStatusCode.OK, "Collection: ${req.name}")
    }
}