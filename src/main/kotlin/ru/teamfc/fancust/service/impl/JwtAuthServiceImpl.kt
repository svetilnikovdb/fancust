package ru.teamfc.fancust.service.impl

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.teamfc.fancust.dto.auth.JwtUserPayload
import ru.teamfc.fancust.service.JwtAuthService

@Service
class JwtAuthServiceImpl : JwtAuthService {
    override fun getAuthPayLoad(): JwtUserPayload =
        SecurityContextHolder.getContext().authentication.principal as JwtUserPayload
}