package shared.error

import kotlinx.serialization.Serializable

/**
 * フロントエンド向けの共通エラーレスポンス。
 * RFC9457 Problem+JSONに近い構造。
 */
@Serializable
data class ErrorResponse(
    val code: String,
    val status: Int,
    val title: String,
    val detail: String? = null,
    val traceId: String? = null,
    val errors: Map<String, String>? = null
)
