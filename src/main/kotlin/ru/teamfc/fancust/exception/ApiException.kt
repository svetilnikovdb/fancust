package ru.teamfc.fancust.exception

import java.lang.RuntimeException
import org.springframework.http.HttpStatus

data class ApiException(
    val httpStatus: HttpStatus,
    val code: String,
    override val message: String?
) : RuntimeException()