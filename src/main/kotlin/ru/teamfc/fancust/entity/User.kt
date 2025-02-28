package ru.teamfc.fancust.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import ru.teamfc.fancust.admin.Role

@Entity
@Table(name = "users")
@JsonNaming(SnakeCaseStrategy::class)
data class User(
    @Id
    @GeneratedValue
    @field:Schema(description = "Ник, который указывается при регистрации", required = true)
    val id: String, // ==nickname,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val birthDate: LocalDate?,

//    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    @JoinColumn(name = "login_info_value") ////
//    @Column(name = "login_info")
//    val loginInfo: LoginInfo,
    val loginValue: String, ////менять в @Transactional
    val isActive: Boolean = true,
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Enumerated(EnumType.STRING)
    val role: Role // заполняется при регистрации, может менять ADMIN(мб суперадмин)


//   потом какую нить другую информацию закинуть необязательную
    //    @fetchtype = lazy
//    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
//    val loginsInfo: List<LoginInfo> = emptyList(),
)