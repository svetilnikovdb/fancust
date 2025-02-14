package ru.teamfc.fancust.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.persistence.Access
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.NamedAttributeNode
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy
import ru.teamfc.fancust.admin.Role
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "users")
@JsonNaming(SnakeCaseStrategy::class)
data class User(
    @Id
    @GeneratedValue
    val id: UUID,
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