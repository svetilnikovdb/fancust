package ru.teamfc.fancust.admin.service

import java.time.Instant
import java.util.Date
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.teamfc.fancust.repository.RefreshTokenRepository
import ru.teamfc.fancust.service.JwtService

@Service
@Transactional
class ScheduledTaskService(
    private val refreshTokenRepository: RefreshTokenRepository
) {

    @Scheduled(cron = "0 0 5 * * *") // каждый день в 5 утра
    fun cleanExpiredRefreshTokens() {
        val now = Date.from(Instant.now())
        val deletedTokensCount = refreshTokenRepository.deleteByExpiresAtLessThan(now)
        log.info("Removed refresh tokens on schedule: $deletedTokensCount")
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(ScheduledTaskService::class.java)
    }
}