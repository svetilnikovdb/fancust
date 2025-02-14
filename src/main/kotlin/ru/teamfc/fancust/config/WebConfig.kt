package ru.teamfc.fancust.config

import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.annotation.RequestScope
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.teamfc.fancust.common.HeadersDto

@Configuration
class WebConfig : WebMvcConfigurer {
    @Bean
    @RequestScope
    fun headersDto(request: HttpServletRequest) = HeadersDto(request)
}