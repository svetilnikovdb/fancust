package ru.teamfc.fancust.common

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.constraints.NotBlank
import org.springframework.stereotype.Component
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.context.annotation.RequestScope

@Component
//@RequestScope
data class HeadersDto(private val request: HttpServletRequest) {
    val authorization = request.getHeader("authorization") ?: null
    val userAgent = request.getHeader("user-agent") ?: null
    val xRequestId = request.getHeader("x-request-id") ?: null
    val ip = request.getHeader("ip") ?: null
    @NotBlank(message = "Header device-id is required")
    val deviceId: String = request.getHeader("device-id")
}


//package ru.teamfc.fancust.common
//
//import jakarta.servlet.http.HttpServletRequest
//import org.springframework.stereotype.Component
//
//@Component
//data class HeadersDto (
//    val authorization: String?,
//    val userAgent: String,
//    val xRequestId: String,
//    val ip: String
//) {
//
//    constructor(request: HttpServletRequest) : this(
//        authorization = request.getHeader("authorization"),
//        userAgent = request.getHeader("user-agent"),
//        xRequestId = request.getHeader("x-request-id"),
//        ip = request.getHeader("ip")
//    )
//}
