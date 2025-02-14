package ru.teamfc.fancust.service

import ru.teamfc.fancust.dto.auth.JwtUserPayload
import ru.teamfc.fancust.dto.auth.Token

interface TokenService {
    fun buildTokenPair(jwtUserPayload: JwtUserPayload): Token
}