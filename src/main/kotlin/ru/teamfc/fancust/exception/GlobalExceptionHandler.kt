package ru.teamfc.fancust.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.teamfc.fancust.common.HeadersDto
import ru.teamfc.fancust.dto.auth.common.ErrorResponse

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class) //// убрать request?
    fun handleCommonException(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        logger.error("Exception was thrown:")
        val error = ErrorResponse(
            requestId = getRequestId(request),
            code = "INTERNAL_SERVER_ERROR",
            message = "Внутренняя ошибка сервера",
            description = e.message
        )

        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun getRequestId(request: HttpServletRequest): String =
        request.getHeader("x-request-id")


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}