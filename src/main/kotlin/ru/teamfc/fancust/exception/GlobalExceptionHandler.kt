package ru.teamfc.fancust.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.teamfc.fancust.dto.common.ErrorResponse

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleCommonException(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error("Exception was thrown:")
        val error = ErrorResponse(
            requestId = getRequestId(request),
            code = "INTERNAL_SERVER_ERROR",
            info = "Внутренняя ошибка сервера",
            reason = e.message
        )

        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ApiException::class)
    fun handleApiException(e: ApiException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error("ApiException was thrown: httpStatus: ${e.httpStatus}; code: ${e.code}; reason: ${e.info}")
        val error = ErrorResponse(
            requestId = getRequestId(request),
            code = e.code,
            info = e.info,
            reason = e.cause?.toString()
        )

        return ResponseEntity(error, e.httpStatus)
    }

    private fun getRequestId(request: HttpServletRequest): String =
        request.getHeader("x-request-id")

    companion object {
        private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}