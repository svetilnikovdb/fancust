package ru.teamfc.fancust.dto.auth

import io.jsonwebtoken.Claims
import ru.teamfc.fancust.admin.Role

data class JwtUserPayload(
    val userId: String,
    val deviceId: String,
//    val isAuthorized: Boolean, проверять по Role
    val role: Role
) {
    fun toClaims(): Map<String, Any> =
        mapOf(
            "userId" to userId,
            "deviceId" to deviceId,
            "role" to role,
        )

    fun isAuthenticated() = role.ordinal > Role.GUEST.ordinal ////

    companion object {
        fun of(claims: Claims) = JwtUserPayload(
            userId = claims.get("userId", String::class.java),
            deviceId = claims.get("deviceId", String::class.java),
            role = Role.valueOf(claims.get("role", String::class.java))
        )
    }
}