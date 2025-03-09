package ru.teamfc.fancust.service.impl

import java.util.UUID
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.teamfc.fancust.admin.Role
import ru.teamfc.fancust.common.HeadersDto
import ru.teamfc.fancust.dto.auth.JwtUserPayload
import ru.teamfc.fancust.dto.auth.Token
import ru.teamfc.fancust.dto.auth.UserDto.Companion.toUserDto
import ru.teamfc.fancust.dto.request.RefreshTokenRequest
import ru.teamfc.fancust.dto.request.SignInRequest
import ru.teamfc.fancust.dto.request.SignUpRequest
import ru.teamfc.fancust.dto.response.AuthResponse
import ru.teamfc.fancust.entity.User
import ru.teamfc.fancust.exception.ApiError
import ru.teamfc.fancust.service.AuthService
import ru.teamfc.fancust.service.JwtService
import ru.teamfc.fancust.service.TokenService
import ru.teamfc.fancust.service.UserService

@Service
class AuthServiceImpl(
    private val tokenService: TokenService,
//    private val jwtAuthService: JwtAuthService,
    private val headers: HeadersDto,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
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
        val authPayload = buildAuthPayload(user)
        return buildAuthResponse(authPayload)
    }

    override fun signIn(request: SignInRequest): AuthResponse {
        val user = userService.findByLogin(request.login)
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw ApiError.WRONG_PASSWORD.getException()
        }
        val authPayload = buildAuthPayload(user)
        return buildAuthResponse(authPayload)
    }

    override fun refreshToken(request: RefreshTokenRequest): AuthResponse {
        val token = request.token
        if (isRefreshTokenValid(token)) {
            val claims = jwtService.extractRefreshClaims(token)
            val authPayload = JwtUserPayload.of(claims)
            return AuthResponse(tokenService.buildTokenPair(authPayload))
        }
        throw ApiError.WRONG_TOKEN.getException()
    }

    private fun isRefreshTokenValid(token: String): Boolean {
        if (!jwtService.validateRefreshToken(token)) {
            return false
        }
        val deviceId = headers.deviceId
        val foundToken = tokenService.getRefreshToken(deviceId)?.token
        return passwordEncoder.matches(token, foundToken)
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

    private fun buildAuthPayload(user: User): JwtUserPayload =
        JwtUserPayload(
            userId = user.nickName,
            deviceId = headers.deviceId,
            role = user.role
        )

}