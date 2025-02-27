package ru.teamfc.fancust.dto.auth.common

import io.swagger.v3.oas.annotations.media.Schema

data class ErrorResponse(
    @field:Schema(description = "id запроса", nullable = true)
    val requestId: String,
    @field:Schema(description = "Код ошибки", nullable = true, example = "user_is_not_authorized")
    val code: String,
    @field:Schema(description = "Причина ошибки", nullable = true, example = "Необходимо авторизоваьтся")
    val info: String?,
    @field:Schema(description = "Указание на место ошибки", nullable = true, example = "java.lang.Throwable: ")
    val reason: String?,
)