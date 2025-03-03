package ru.teamfc.fancust.dto.auth

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import ru.teamfc.fancust.admin.Role
import ru.teamfc.fancust.dto.request.SignUpRequest

data class UserDto(
    val nickName: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val email: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDate?,
    val isActive: Boolean = true,
    val role: Role
) {
    companion object {
        fun SignUpRequest.toUserDto(): UserDto = UserDto(
            nickName = nickName,
            firstName = firstName,
            lastName = lastName,
            middleName = middleName,
            password = password,
            birthDate = birthDate,
            email = email,
            role = Role.USER
        )
    }
}