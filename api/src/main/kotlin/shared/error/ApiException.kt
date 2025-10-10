package shared.error

/**
 * アプリケーション全体で使う共通の例外クラス。
 * コードと任意の詳細メッセージを保持する。
 */
open class ApiException(
    val code: ErrorCode,
    override val message: String? = null,
    val causeException: Throwable? = null
) : RuntimeException(message, causeException)

/**
 * よく使う具象例外。
 */
class ValidationException(details: Map<String, String>? = null) :
    ApiException(ErrorCode.VALIDATION_ERROR, details?.toString())

class UnauthorizedException : ApiException(ErrorCode.UNAUTHORIZED)
class ForbiddenException : ApiException(ErrorCode.FORBIDDEN)
class NotFoundException(message: String? = null) : ApiException(ErrorCode.NOT_FOUND, message)
class ConflictException(message: String? = null) : ApiException(ErrorCode.CONFLICT, message)
class TooManyRequestsException(message: String? = null) :
    ApiException(ErrorCode.TOO_MANY_REQUESTS, message)
