package ru.teamfc.fancust.dto.request

import jakarta.validation.constraints.NotBlank

data class SignInRequest(
    @field:NotBlank(message = "login is required")
    val login: String,
    @field:NotBlank(message = "password is required")
    val password: String
)