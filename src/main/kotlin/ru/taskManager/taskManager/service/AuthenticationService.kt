package ru.taskManager.taskManager.service

import org.springframework.security.authentication.AuthenticationManager
import ru.taskManager.taskManager.api.response.AuthenticationResponse
import ru.taskManager.taskManager.api.request.LoginRequest
import ru.taskManager.taskManager.api.request.RegisterRequest
import ru.taskManager.taskManager.repository.UserRepository
import ru.taskManager.taskManager.service.JwtService

interface AuthenticationService {
    val authManager: AuthenticationManager
    val repository: UserRepository
    val jwtService: JwtService

    fun authenticate(loginRequest: LoginRequest) : AuthenticationResponse
    fun register(registerRequest: RegisterRequest) : AuthenticationResponse

}