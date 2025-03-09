package ru.teamfc.fancust.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.teamfc.fancust.common.HeadersDto
import ru.teamfc.fancust.dto.common.ErrorResponse

@ControllerAdvice
class GlobalExceptionHandler(
    val headers: HeadersDto
) {

    @ExceptionHandler(Exception::class)
    fun handleCommonException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("Exception was thrown:  $e")
        val error = ErrorResponse(
            requestId = headers.xRequestId,
            code = "INTERNAL_SERVER_ERROR",
            info = "Внутренняя ошибка сервера",
            reason = e.message
        )

        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error("AuthenticationException was thrown: $e")
        val error = ErrorResponse(
            requestId = headers.xRequestId,
            code = "unauthorized",
            info = "Для доступа к реcурсу требуется полная аутентификация",
            reason = e.message
        )

        return ResponseEntity(error, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ApiException::class)
    fun handleApiException(e: ApiException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error("ApiException was thrown: httpStatus: ${e.httpStatus}; code: ${e.code}; reason: ${e.info}")
        val error = ErrorResponse(
            requestId = headers.xRequestId,
            code = e.code,
            info = e.info,
            reason = e.cause?.toString()
        )

        return ResponseEntity(error, e.httpStatus)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}