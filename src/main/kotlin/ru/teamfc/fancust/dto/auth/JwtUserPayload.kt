package ru.teamfc.fancust.dto.auth

import io.jsonwebtoken.Claims
import ru.teamfc.fancust.admin.Role
import java.util.*

data class JwtUserPayload(
    val userId: UUID,
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
            userId = claims.get("userId", UUID::class.java),
            deviceId = claims.get("deviceId", String::class.java),
            role = claims.get("role", Role::class.java)
        )
    }
}