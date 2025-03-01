package ru.teamfc.fancust.service.impl

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.teamfc.fancust.dto.auth.UserDto
import ru.teamfc.fancust.entity.User
import ru.teamfc.fancust.exception.ApiError
import ru.teamfc.fancust.repository.UserRepository
import ru.teamfc.fancust.service.UserService

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {
    override fun save(userDto: UserDto): User {
        checkIfUserDataExists(userDto)
        val user = userDto.let {
            User(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                middleName = it.middleName,
                password = passwordEncoder.encode(it.password),
                email = it.email,
                birthDate = it.birthDate,
                role = it.role
            )
        }
        return userRepository.save(user)
    }

    private fun checkIfUserDataExists(userDto: UserDto) {
        if (userRepository.existsById(userDto.id)) {
            throw ApiError.NICK_ALREADY_EXISTS.getException()
        }

        if (userRepository.existsByEmail(userDto.email)) {
            throw ApiError.EMAIL_ALREADY_EXISTS.getException()
        }
    }

}