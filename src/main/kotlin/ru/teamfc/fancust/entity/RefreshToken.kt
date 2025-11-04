package ru.teamfc.fancust.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.Date

@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RefreshToken(
    @Id
    val deviceId: String, ////будет в хедерах, userId нельзя, тк будет выкидывать при входе с нового ус-ва
    @Column(columnDefinition = "VARCHAR(512)")
    var token: String,
    val expiresAt: Date
)