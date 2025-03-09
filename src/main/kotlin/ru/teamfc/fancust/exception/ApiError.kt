package ru.teamfc.fancust.exception

import org.springframework.http.HttpStatus

enum class ApiError(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val info: String?,
) {
    USER_IS_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "user_is_not_authorized", "Необходимо авторизоваться"),
    NICK_ALREADY_EXISTS(HttpStatus.CONFLICT, "nick_already_exists", "Никнейм занят другим пользователем"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "email_already_exists", "Email занят другим пользователем"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user_not_found", "Пользователь не найден"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "wrong_password", "Неверный пароль"),
    WRONG_TOKEN(HttpStatus.BAD_REQUEST, "wrong_token", "Неверный токен");

    fun getException(): ApiException = ApiException(httpStatus, code, info)
}