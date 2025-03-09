package ru.teamfc.fancust.service

import ru.teamfc.fancust.dto.auth.JwtUserPayload
import ru.teamfc.fancust.dto.auth.Token
import ru.teamfc.fancust.entity.RefreshToken

interface TokenService {
    fun buildTokenPair(jwtUserPayload: JwtUserPayload): Token
    fun getRefreshToken(deviceId: String): RefreshToken?
}