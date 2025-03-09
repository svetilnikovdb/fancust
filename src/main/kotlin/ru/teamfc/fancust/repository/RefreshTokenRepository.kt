package ru.teamfc.fancust.repository

import java.util.Date
import org.springframework.data.jpa.repository.JpaRepository
import ru.teamfc.fancust.entity.RefreshToken

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByDeviceId(id: String): RefreshToken?
    fun deleteByExpiresAtLessThan(now: Date): Int
}