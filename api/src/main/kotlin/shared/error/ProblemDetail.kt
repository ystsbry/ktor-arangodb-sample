package shared.error

import kotlinx.serialization.Serializable

/**
 * RFC9457 "Problem Details for HTTP APIs" 準拠モデル。
 * ErrorResponseより汎用的なAPI層用。
 */
@Serializable
data class ProblemDetail(
    val type: String = "about:blank",
    val title: String,
    val status: Int,
    val detail: String? = null,
    val instance: String? = null,
    val code: String? = null,
    val traceId: String? = null,
    val errors: Map<String, String>? = null
)
