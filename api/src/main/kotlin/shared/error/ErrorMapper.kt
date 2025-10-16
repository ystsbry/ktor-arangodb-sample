package shared.error

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.request.uri
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun Application.configureErrorMapper() {
    install(StatusPages) {
        exception<ApiException> { call, e ->
            logger.warn(e) { "Handled API exception: ${e.code.name} (${e.code.httpStatus.value}) at ${call.request.uri} - ${e.message ?: e.code.messageKey}" }

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
            logger.error(e) { "Unhandled exception occurred during API call: ${call.request.uri} - ${e::class.simpleName}: ${e.message}" }

            val response = ProblemDetail(
                title = "Internal Server Error",
                status = HttpStatusCode.InternalServerError.value,
                detail = "An unexpected error occurred",
                code = ErrorCode.INTERNAL_ERROR.name
            )

            call.respond(HttpStatusCode.InternalServerError, response)
        }
    }
}
