package ru.teamfc.fancust.dto.request

import com.fasterxml.jackson.annotation.JsonCreator

data class RefreshTokenRequest @JsonCreator constructor(
    val token: String
)