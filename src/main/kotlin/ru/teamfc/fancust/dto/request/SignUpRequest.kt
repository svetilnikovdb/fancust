package ru.teamfc.fancust.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class SignUpRequest(
    @field:NotBlank(message = "nickName is required")
    val nickName: String,
    @field:NotBlank(message = "Password is required") //// потом добавить аннотацию для пароля
    val password: String,
    @field:NotBlank(message = "firstName is required")
    val firstName: String,
    @field:NotBlank(message = "lastName is required")
    val lastName: String,
    val middleName: String?,
    @field:NotBlank(message = "email is required")
    val email: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDate?
)