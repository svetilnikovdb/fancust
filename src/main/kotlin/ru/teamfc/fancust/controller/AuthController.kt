package ru.teamfc.fancust.controller

import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.teamfc.fancust.dto.common.DataResponse
import ru.teamfc.fancust.dto.request.RefreshTokenRequest
import ru.teamfc.fancust.dto.request.SignInRequest
import ru.teamfc.fancust.dto.request.SignUpRequest
import ru.teamfc.fancust.dto.response.AuthResponse
import ru.teamfc.fancust.service.AuthService
import ru.teamfc.fancust.utils.toDataResponse

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/guest")
    @Operation(summary = "Создать гостя")
    fun createGuest(): ResponseEntity<DataResponse<AuthResponse>> =
        authService.createGuest().toDataResponse()

    @PostMapping("/signUp")
    @Operation(summary = "Регистрация")
    fun signUp(@RequestBody @Valid request: SignUpRequest): ResponseEntity<DataResponse<AuthResponse>> =
        authService.signUp(request).toDataResponse()

    @PostMapping("/signIn")
    @Operation(summary = "Авторизация")
    fun signIn(@RequestBody @Valid request: SignInRequest): ResponseEntity<DataResponse<AuthResponse>> =
        authService.signIn(request).toDataResponse()

    @PostMapping("/refresh")
    @Operation(summary = "Обновить токен")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<DataResponse<AuthResponse>> =
        authService.refreshToken(request).toDataResponse()
}