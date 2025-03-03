package ru.teamfc.fancust.service

import ru.teamfc.fancust.dto.auth.UserDto
import ru.teamfc.fancust.entity.User

interface UserService {
    fun save(userDto: UserDto): User
    fun findByLogin(login: String): User
}