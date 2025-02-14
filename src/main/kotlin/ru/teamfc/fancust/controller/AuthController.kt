package ru.teamfc.fancust.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.teamfc.fancust.dto.response.AuthResponse
import ru.teamfc.fancust.service.AuthService

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/guest")
    fun createGuest(): AuthResponse =
        authService.createGuest()
}