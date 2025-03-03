package ru.teamfc.fancust.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.time.LocalDate
import ru.teamfc.fancust.admin.Role
import ru.teamfc.fancust.admin.entity.UUIDBaseEntity

@Entity
@Table(
    name = "users",
    indexes = [
        Index(name = "idx_email", columnList = "email", unique = true),
        Index(name = "idx_nickName", columnList = "nickName", unique = true)
    ]
)
@JsonNaming(SnakeCaseStrategy::class)
data class User(
    @field:Schema(description = "Ник, который указывается при регистрации", required = true)
    @Column(unique = true)
    val nickName: String,
    @Column(nullable = false)
    val firstName: String,
    @Column(nullable = false)
    val lastName: String,
    val middleName: String?,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthDate: LocalDate?,

//    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    @JoinColumn(name = "login_info_value") ////
//    @Column(name = "login_info")
//    val loginInfo: LoginInfo,
//    val loginValue: String,
    @JsonIgnore
    val password: String,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val isActive: Boolean = true,
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: Role,


//   потом какую нить другую информацию закинуть необязательную
    //    @fetchtype = lazy
//    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
//    val loginsInfo: List<LoginInfo> = emptyList(),
) : UUIDBaseEntity()