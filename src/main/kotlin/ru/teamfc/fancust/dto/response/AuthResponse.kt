package ru.teamfc.fancust.dto.response

import ru.teamfc.fancust.dto.auth.Token

data class AuthResponse(
    val token: Token
)