package ru.teamfc.fancust.service.impl

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.teamfc.fancust.dto.auth.JwtUserPayload
import ru.teamfc.fancust.dto.auth.RefreshTokenDto
import ru.teamfc.fancust.service.JwtService
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.function.Function
import javax.crypto.SecretKey

@Service
class JwtServiceImpl(
    @Value("\${security.jwt.secret.access}")
    private val accessTokenSecret: String,
    @Value("\${security.jwt.secret.refresh}")
    private val refreshTokenSecret: String,
    @Value("\${security.jwt.ttl.access}")
    private val accessTokenExpirationTime: Long,
    @Value("\${security.jwt.ttl.refresh}")
    private val refreshTokenExpirationTime: Long,
): JwtService {

    override fun generateAccessToken(payload: JwtUserPayload): String {
        val expiration = getExpirationTime(accessTokenExpirationTime)
        val signWith = getSigningKey(accessTokenSecret)

        return buildToken(payload, expiration, signWith)
    }

    override fun generateRefreshToken(payload: JwtUserPayload): RefreshTokenDto {
        val expiration = getExpirationTime(refreshTokenExpirationTime)
        val signWith = getSigningKey(refreshTokenSecret)
        val token = buildToken(payload, expiration, signWith)
        return RefreshTokenDto(token, expiration)
    }

    private fun getExpirationTime(tokenExpirationTime: Long): Date {
        val now = LocalDateTime.now().atZone(ZoneId.systemDefault())
        val expirationInstant = now.plusSeconds(tokenExpirationTime).toInstant()
        return Date.from(expirationInstant)
    }

    private fun getSigningKey(secret: String) =
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)) //// в примере BASE64URL

    private fun buildToken(payload: JwtUserPayload, expiration: Date, signWith: SecretKey): String {
        val jwtBuilder = Jwts.builder()
            .subject(payload.deviceId)
            .claims(payload.toClaims())
            .issuedAt(Date())
            .expiration(expiration)
            .signWith(signWith)

        return jwtBuilder.compact()
    }

    override fun validateAccessToken(token: String): Boolean {
        return validateToken(token, accessTokenSecret)
    }

    override fun validateRefreshToken(token: String): Boolean {
        return validateToken(token, refreshTokenSecret)
    }

    private fun validateToken(token: String, secret: String): Boolean {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey(secret))
                .build()
                .parseSignedClaims(token)
                .payload
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: $e")
        } catch (e: MalformedJwtException) {
            logger.error("Malformed JWT: $e")
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: $e")
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: $e")
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: $e")
        }
        return false
    }

    override fun <T> extractAccessClaim(token: String, resolver: Function<Claims, T>): T {
        val claims = extractClaims(token, accessTokenSecret)
        return resolver.apply(claims)
    }

    override fun extractAccessClaims(token: String): Claims =
        extractClaims(token, accessTokenSecret)

    fun <T> extractRefreshClaim(token: String, resolver: Function<Claims, T>): T {
        val claims = extractClaims(token, accessTokenSecret)
        return resolver.apply(claims)
    }

    override fun extractRefreshClaims(token: String): Claims =
        extractClaims(token, refreshTokenSecret)

    fun extractClaims(token: String, secret: String): Claims {
        return Jwts.parser()
            .verifyWith(getSigningKey(secret))
            .build()
            .parseSignedClaims(token)
            .payload
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JwtService::class.java)
    }
}