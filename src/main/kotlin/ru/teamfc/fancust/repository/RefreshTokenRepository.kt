package ru.teamfc.fancust.repository

import java.util.Date
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import ru.teamfc.fancust.entity.RefreshToken

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByDeviceId(id: String): Optional<RefreshToken>
    fun deleteByExpiresAtLessThan(now: Date): Int
}