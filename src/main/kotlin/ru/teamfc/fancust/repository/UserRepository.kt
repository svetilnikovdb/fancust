package ru.teamfc.fancust.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.teamfc.fancust.entity.User

interface UserRepository : JpaRepository<User, String> {
    fun existsByEmail(loginValue: String): Boolean
}