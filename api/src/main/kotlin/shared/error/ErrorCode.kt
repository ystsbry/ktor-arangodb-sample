package shared.error

import io.ktor.http.*

/**
 * 共通のエラーコード定義。
 * 各コードに対応するHTTPステータスとメッセージキーを保持する。
 */
enum class ErrorCode(
    val httpStatus: HttpStatusCode,
    val messageKey: String
) {
    VALIDATION_ERROR(HttpStatusCode.BadRequest, "error.validation"),
    UNAUTHORIZED(HttpStatusCode.Unauthorized, "error.unauthorized"),
    FORBIDDEN(HttpStatusCode.Forbidden, "error.forbidden"),
    NOT_FOUND(HttpStatusCode.NotFound, "error.not_found"),
    CONFLICT(HttpStatusCode.Conflict, "error.conflict"),
    TOO_MANY_REQUESTS(HttpStatusCode.TooManyRequests, "error.rate_limit"),
    INTERNAL_ERROR(HttpStatusCode.InternalServerError, "error.internal");

    companion object {
        fun from(status: HttpStatusCode): ErrorCode =
            values().find { it.httpStatus == status } ?: INTERNAL_ERROR
    }
}
