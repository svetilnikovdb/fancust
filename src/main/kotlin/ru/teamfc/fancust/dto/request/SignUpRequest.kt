package ru.teamfc.fancust.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate

data class SignUpRequest(
    @field:NotBlank
    val nickName: String,
    @field:NotBlank(message = "Password is required") //// потом добавить аннотацию для пароля
    val password: String,
    @field:NotBlank
    val firstName: String,
    @field:NotBlank
    val lastName: String,
    val middleName: String?,
    val email: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDate?
)