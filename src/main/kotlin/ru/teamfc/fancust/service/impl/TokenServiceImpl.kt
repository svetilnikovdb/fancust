package ru.teamfc.fancust.service.impl

import java.time.Instant
import java.util.Date
import org.springframework.stereotype.Service
import ru.teamfc.fancust.dto.auth.JwtUserPayload
import ru.teamfc.fancust.dto.auth.Token
import ru.teamfc.fancust.entity.RefreshToken
import ru.teamfc.fancust.repository.RefreshTokenRepository
import ru.teamfc.fancust.service.JwtService
import ru.teamfc.fancust.service.TokenService

@Service
class TokenServiceImpl(
    private val jwtService: JwtService,
    private val refreshTokenRepository: RefreshTokenRepository
) : TokenService {
    override fun buildTokenPair(jwtUserPayload: JwtUserPayload): Token {
        val refreshToken = jwtService.generateRefreshToken(jwtUserPayload)
        val accessToken = jwtService.generateAccessToken(jwtUserPayload)
        val refreshTokenEntity = RefreshToken(jwtUserPayload.deviceId, refreshToken.token, refreshToken.expiration)
        refreshTokenRepository.save(refreshTokenEntity)
        return Token(accessToken = accessToken, refreshToken = refreshToken.token)
    }
}