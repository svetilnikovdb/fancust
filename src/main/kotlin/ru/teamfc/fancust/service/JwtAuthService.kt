package ru.teamfc.fancust.service

import ru.teamfc.fancust.dto.auth.JwtUserPayload

interface JwtAuthService {
    fun getAuthPayLoad(): JwtUserPayload
}