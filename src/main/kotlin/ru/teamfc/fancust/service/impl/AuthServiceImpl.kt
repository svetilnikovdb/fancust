package ru.teamfc.fancust.service.impl

import org.springframework.stereotype.Service
import ru.teamfc.fancust.admin.Role
import ru.teamfc.fancust.common.HeadersDto
import ru.teamfc.fancust.dto.auth.JwtUserPayload
import ru.teamfc.fancust.dto.response.AuthResponse
import ru.teamfc.fancust.service.AuthService
import ru.teamfc.fancust.service.JwtAuthService
import ru.teamfc.fancust.service.JwtService
import ru.teamfc.fancust.service.TokenService
import java.util.*

@Service
class AuthServiceImpl(
    private val tokenService: TokenService,
//    private val jwtAuthService: JwtAuthService,
    private val headers: HeadersDto
) : AuthService {
    override fun createGuest(): AuthResponse {
        val authPayload = JwtUserPayload(
            userId = UUID.randomUUID(),
            deviceId = headers.deviceId,
            role = Role.GUEST
        )

        return tokenService.buildTokenPair(authPayload)
            .let { AuthResponse(it.accessToken, it.refreshToken) }
    }
}