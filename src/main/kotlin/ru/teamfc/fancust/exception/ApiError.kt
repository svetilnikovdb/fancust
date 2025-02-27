package ru.teamfc.fancust.exception

import org.springframework.http.HttpStatus

enum class ApiError(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val info: String?,
) {
    USER_IS_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "user_is_not_authorized", "Необходимо авторизоваться");

    fun getException(): ApiException = ApiException(httpStatus, code, info)
}