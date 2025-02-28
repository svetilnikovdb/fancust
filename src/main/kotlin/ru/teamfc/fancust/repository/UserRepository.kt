package ru.teamfc.fancust.repository

import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository
import ru.teamfc.fancust.entity.User

interface UserRepository : JpaRepository<User, Long> {
    fun findById(id: String): Optional<User>
    fun findByLoginValue(loginValue: String)
}