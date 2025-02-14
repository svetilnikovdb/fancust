package ru.teamfc.fancust.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import ru.teamfc.fancust.enums.LoginType
import java.util.UUID

@Entity
@Table(name = "logins_info")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class LoginInfo(
    @Enumerated(EnumType.STRING)
    val loginType: LoginType,
    @Id
    val value: String,
    @field:Schema(description = "Подтвержден ли данный тип логина", required = true)
    val isConfirmed: Boolean,
    val userId: UUID
)