package ru.teamfc.fancust.filter

import io.jsonwebtoken.Claims
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.teamfc.fancust.admin.Role
import ru.teamfc.fancust.dto.auth.JwtUserPayload
import ru.teamfc.fancust.service.JwtService

@Component
class JwtAuthorizationFilter(
    private val jwtService: JwtService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)

        val deviceId = jwtService.extractAccessClaim(token, Claims::getSubject)

        if (isValidSession(deviceId, token)) {
            val claims = jwtService.extractAccessClaims(token)
            val jwtPayload = JwtUserPayload.of(claims)
            val authorities = Role.getInheritedRoles(jwtPayload.role)
                .map { role -> SimpleGrantedAuthority("ROLE_$role") }

            val authentication = UsernamePasswordAuthenticationToken(jwtPayload, null, authorities)
//            authentication.details = jwtPayload ////зачем??? Сюда обычно доп инфа по типу ip-адреса
//            authentication.isAuthenticated = true ставится по умолчанию в конструкторе

            SecurityContextHolder.getContext().authentication = authentication

        }
        filterChain.doFilter(request, response)
    }

    private fun isValidSession(deviceId: String?, token: String) =
        SecurityContextHolder.getContext().authentication == null
                && !deviceId.isNullOrBlank()
                && jwtService.validateAccessToken(token)
}