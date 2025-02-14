package ru.teamfc.fancust.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.teamfc.fancust.entity.User
import java.util.Optional
import java.util.UUID

interface UserRepository : JpaRepository<User, Long> {
    fun findById(id: UUID): Optional<User>
    fun findByLoginValue(loginValue: String)
}