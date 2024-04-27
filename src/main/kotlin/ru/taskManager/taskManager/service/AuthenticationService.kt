package ru.taskManager.taskManager.service

import org.springframework.security.authentication.AuthenticationManager
import ru.taskManager.taskManager.api.response.AuthenticationResponse
import ru.taskManager.taskManager.api.request.user.LoginRequest
import ru.taskManager.taskManager.api.request.user.RegisterRequest
import ru.taskManager.taskManager.repository.UserRepository

interface AuthenticationService {
    val authManager: AuthenticationManager
    val repository: UserRepository
    val jwtService: JwtService

    fun authenticate(loginRequest: LoginRequest) : AuthenticationResponse
    fun register(registerRequest: RegisterRequest) : AuthenticationResponse

}