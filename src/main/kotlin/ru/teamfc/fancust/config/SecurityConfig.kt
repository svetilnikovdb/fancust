package ru.teamfc.fancust.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.util.AntPathMatcher
import ru.teamfc.fancust.filter.JwtAuthenticationEntryPoint
import ru.teamfc.fancust.filter.JwtAuthorizationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthorizationFilter: JwtAuthorizationFilter,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors(Customizer.withDefaults())
            .csrf { it.disable() } // так как аутентификация происходит с помощью JWT
            .authorizeHttpRequests {
                it
                    .requestMatchers(*requestMatchersForAll) //// здесь указать свои
                    .permitAll()
//                    .requestMatchers("/admin/**").hasAnyAuthority("ADMIN") //// потом раскомментить
                    .anyRequest()
                    .fullyAuthenticated() // остальные запросы требуют полной аутентификации
            }
            .exceptionHandling {
                it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration):AuthenticationManager =
        config.authenticationManager

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    companion object {
        private val requestMatchersForAll = arrayOf("/api/v1/auth/guest", "/api/v1/auth/refresh")
    }

}