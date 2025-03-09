package ru.teamfc.fancust.service

import io.jsonwebtoken.Claims
import ru.teamfc.fancust.dto.auth.JwtUserPayload
import ru.teamfc.fancust.dto.auth.RefreshTokenDto
import java.util.function.Function

interface JwtService {
    fun generateAccessToken(payload: JwtUserPayload): String
    fun generateRefreshToken(payload: JwtUserPayload): RefreshTokenDto
    fun <T> extractAccessClaim(token: String, resolver: Function<Claims, T>): T
    fun extractAccessClaims(token: String): Claims
    fun extractRefreshClaims(token: String): Claims
    fun validateAccessToken(token: String): Boolean
    fun validateRefreshToken(token: String): Boolean
}