package ru.teamfc.fancust.dto.auth

import java.util.Date

class RefreshTokenDto(
    val token: String,
    val expiration: Date
)