package ru.teamfc.fancust.service.impl

import java.util.UUID
import org.springframework.stereotype.Service
import ru.teamfc.fancust.admin.Role
import ru.teamfc.fancust.common.HeadersDto
import ru.teamfc.fancust.dto.auth.JwtUserPayload
import ru.teamfc.fancust.dto.auth.Token
import ru.teamfc.fancust.dto.auth.UserDto.Companion.toUserDto
import ru.teamfc.fancust.dto.request.SignUpRequest
import ru.teamfc.fancust.dto.response.AuthResponse
import ru.teamfc.fancust.service.AuthService
import ru.teamfc.fancust.service.TokenService
import ru.teamfc.fancust.service.UserService

@Service
class AuthServiceImpl(
    private val tokenService: TokenService,
//    private val jwtAuthService: JwtAuthService,
    private val headers: HeadersDto,
    private val userService: UserService,
) : AuthService {
    override fun createGuest(): AuthResponse {
        val authPayload = JwtUserPayload(
            userId = UUID.randomUUID().toString(),
            deviceId = headers.deviceId,
            role = Role.GUEST
        )

        return buildAuthResponse(authPayload)
    }

    override fun signUp(request: SignUpRequest): AuthResponse {
        val user = userService.save(request.toUserDto())
        val authPayload = JwtUserPayload(
            userId = user.nickName,
            deviceId = headers.deviceId,
            role = user.role
        )
        return buildAuthResponse(authPayload)
    }

    private fun buildAuthResponse(authPayload: JwtUserPayload): AuthResponse {
        val tokenPair = tokenService.buildTokenPair(authPayload)
        return AuthResponse(
            token = Token(
                accessToken = tokenPair.accessToken,
                refreshToken = tokenPair.refreshToken
            )
        )
    }

}