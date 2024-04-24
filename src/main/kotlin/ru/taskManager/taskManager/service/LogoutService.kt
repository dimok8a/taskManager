package ru.taskManager.taskManager.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service
import ru.taskManager.taskManager.repository.UserRepository


@Service
class LogoutService
    (
    private val userRepository: UserRepository
    )
    : LogoutHandler {
    override fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ) {
        try {
            val authHeader = request.getHeader("Authorization")
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return
            }
            val jwt = authHeader.substring(7)
            val userByToken = userRepository.findByToken(jwt).orElse(null)
            if (userByToken != null)
            {
                userByToken.token = ""
                userRepository.save(userByToken)
            }
            SecurityContextHolder.clearContext()
        } catch (exception: Exception) {
            println(exception)
        }

    }
}
