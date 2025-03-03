package ru.teamfc.fancust.service

import ru.teamfc.fancust.dto.request.SignInRequest
import ru.teamfc.fancust.dto.request.SignUpRequest
import ru.teamfc.fancust.dto.response.AuthResponse

interface AuthService {
    fun createGuest(): AuthResponse
    fun signUp(request: SignUpRequest): AuthResponse
    fun signIn(request: SignInRequest): AuthResponse
}