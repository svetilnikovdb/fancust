package ru.teamfc.fancust.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.teamfc.fancust.dto.common.DataResponse
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

}