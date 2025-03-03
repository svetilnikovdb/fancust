package ru.teamfc.fancust.admin.entity

import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID
import org.hibernate.annotations.UpdateTimestamp

@MappedSuperclass
abstract class BaseEntity<ID : Serializable>(
    @Id
    val id: ID,
    @Column(updatable = false)
    val created: LocalDateTime? = LocalDateTime.now(),
    @UpdateTimestamp
    var updated: LocalDateTime? = null
)

abstract class UUIDBaseEntity : BaseEntity<UUID>(UUID.randomUUID())