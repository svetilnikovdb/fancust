package ru.teamfc.fancust.service.impl

import java.time.LocalDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.eq
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ContextConfiguration
import ru.teamfc.fancust.admin.Role
import ru.teamfc.fancust.common.HeadersDto
import ru.teamfc.fancust.dto.auth.JwtUserPayload
import ru.teamfc.fancust.dto.auth.Token
import ru.teamfc.fancust.dto.request.SignInRequest
import ru.teamfc.fancust.dto.response.AuthResponse
import ru.teamfc.fancust.entity.User
import ru.teamfc.fancust.exception.ApiException
import ru.teamfc.fancust.service.AuthService
import ru.teamfc.fancust.service.JwtService
import ru.teamfc.fancust.service.TokenService
import ru.teamfc.fancust.service.UserService

@SpringBootTest
@ContextConfiguration(classes = [AuthServiceImpl::class])
class AuthServiceTest {
    @MockBean
    private lateinit var tokenService: TokenService
    @MockBean
    private lateinit var headers: HeadersDto
    @MockBean
    private lateinit var userService: UserService
    @MockBean
    private lateinit var passwordEncoder: PasswordEncoder
    @MockBean
    private lateinit var jwtService: JwtService
    @Autowired
    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() {
        `when`(headers.deviceId).thenReturn(DEVICE_ID)
        `when`(tokenService.buildTokenPair(any<JwtUserPayload>())).thenReturn(tokenPair)
    }

    @Nested
    inner class SignIn {
        private val password = "password1"
        private val request = SignInRequest(login = NICK_NAME, password = password)

        @Test
        internal fun testSignIn() {

            `when`(userService.findByLogin(NICK_NAME)).thenReturn(user)
            `when`(passwordEncoder.matches(eq(password), any<String>())).thenReturn(true)

            val actual = authService.signIn(request)

            val expected = AuthResponse(tokenPair)

            Assertions.assertEquals(expected, actual)
        }

        @Test
        internal fun `should throw WRONG_PASSWORD`() {
            `when`(userService.findByLogin(NICK_NAME)).thenReturn(user)
            `when`(passwordEncoder.matches(eq(password),  any<String>())).thenReturn(false)

            val exception = assertThrows<ApiException> { authService.signIn(request) }

            Assertions.assertAll({
                Assertions.assertEquals("wrong_password", exception.code)
                Assertions.assertEquals("Неверный пароль", exception.message)
            })
        }
    }

    companion object {
        private const val NICK_NAME = "nick1"
        private const val DEVICE_ID = "deviceId-1"
        private val tokenPair = Token("accessToken1", "refreshToken1")
        private val user = User(
            nickName = NICK_NAME,
            firstName = "FirstName1",
            lastName = "lastName1",
            middleName = "middleName1",
            birthDate = LocalDate.of(2001, 1, 1),
            password = "hashedPass",
            email = "mail1@gmail.com",
            isActive = true,
            role = Role.USER
        )
    }
}