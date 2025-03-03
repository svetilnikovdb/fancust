package ru.teamfc.fancust.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.teamfc.fancust.entity.User

interface UserRepository : JpaRepository<User, String> {
    fun existsByNickName(nickName: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findByNickName(email: String): User?
    fun findByEmail(email: String): User?
}