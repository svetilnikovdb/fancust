package ru.teamfc.fancust.dto.auth

data class Token(
    val accessToken: String,
    val refreshToken: String
)