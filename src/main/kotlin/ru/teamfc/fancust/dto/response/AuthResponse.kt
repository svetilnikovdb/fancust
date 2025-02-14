package ru.teamfc.fancust.dto.response

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)