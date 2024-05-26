package ru.taskManager.taskManager.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import ru.taskManager.taskManager.entity.user.Role


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val authenticationProvider: DaoAuthenticationProvider,
    private val logoutHandler: LogoutHandler
) {


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors{}
            .securityMatcher("/api/**")
            .csrf { obj: AbstractHttpConfigurer<*, *> -> obj.disable() }
            .exceptionHandling { it.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(POST, "/api/user/login").anonymous()
                    .requestMatchers(POST, "/api/user/register").anonymous()
                    .requestMatchers(GET, "/api/user/**").hasRole(Role.USER.name)
                    .anyRequest()
                    .authenticated()
            }
            .sessionManagement { session ->
                session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
            .logout {
                it
                    .logoutUrl("/api/user/logout")
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler(LogoutSuccessHandler { request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication? -> SecurityContextHolder.clearContext() })
            }

        return http.build()
    }


//    @Bean
//    fun corsConfigurationSource(): CorsConfigurationSource {
//        val configuration = CorsConfiguration()
//        configuration.allowedOrigins = listOf("http://localhost:8005")
//        configuration.allowedMethods = listOf("GET", "POST")
//        configuration.allowedHeaders = listOf("Authorization", "Content-Type")
//        val source = UrlBasedCorsConfigurationSource()
//        source.registerCorsConfiguration("/**", configuration)
//        return source
//    }
}