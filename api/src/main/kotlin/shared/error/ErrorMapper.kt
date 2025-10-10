package shared.error

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureErrorMapper() {
    install(StatusPages) {
        exception<ApiException> { call, e ->
            val code = e.code

            val response = ProblemDetail(
                title = code.name,
                status = code.httpStatus.value,
                detail = e.message ?: code.messageKey,
                code = code.name
            )

            call.respond(code.httpStatus, response)
        }

        exception<Throwable> { call, e ->
            val response = ProblemDetail(
                title = "Internal Server Error",
                status = HttpStatusCode.InternalServerError.value,
                detail = e.message,
                code = ErrorCode.INTERNAL_ERROR.name
            )

            call.respond(HttpStatusCode.InternalServerError, response)
        }
    }
}
