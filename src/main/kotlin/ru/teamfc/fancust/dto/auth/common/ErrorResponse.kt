package ru.teamfc.fancust.dto.auth.common

data class ErrorResponse(
    val requestId: String,
    val code: String,
    val message: String?,
    val description: String?
)