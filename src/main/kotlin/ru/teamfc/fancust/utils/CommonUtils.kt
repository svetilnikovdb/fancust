package ru.teamfc.fancust.utils

import org.springframework.http.ResponseEntity
import ru.teamfc.fancust.dto.auth.common.DataResponse

fun <T> T.toDataResponse(): ResponseEntity<DataResponse<T>> = ResponseEntity.ok(DataResponse(this))