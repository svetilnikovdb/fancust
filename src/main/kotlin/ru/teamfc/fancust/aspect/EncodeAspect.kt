package ru.teamfc.fancust.aspect

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ru.teamfc.fancust.entity.RefreshToken

@Aspect
@Component
class EncodeAspect(
    private val passwordEncoder: PasswordEncoder
) {

    @Before("execution(* ru.teamfc.fancust.repository.RefreshTokenRepository.save(..)) && args(refreshToken)")
    fun encodeRefreshToken(refreshToken: RefreshToken) {
        refreshToken.token = passwordEncoder.encode(refreshToken.token)
    }
}