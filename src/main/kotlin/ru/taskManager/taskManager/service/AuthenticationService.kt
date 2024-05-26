package ru.taskManager.taskManager.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetails
import ru.taskManager.taskManager.api.request.user.ChangeUserDataRequest
import ru.taskManager.taskManager.api.request.user.LoginRequest
import ru.taskManager.taskManager.api.request.user.RegisterRequest
import ru.taskManager.taskManager.api.response.AuthenticationResponse
import ru.taskManager.taskManager.api.response.UserDataResponse
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.UserRepository

interface AuthenticationService {
    val authManager: AuthenticationManager
    val repository: UserRepository
    val jwtService: JwtService

    fun authenticate(loginRequest: LoginRequest): AuthenticationResponse
    fun register(registerRequest: RegisterRequest): AuthenticationResponse
    fun changeUserData(user: User, changeUserDataRequest: ChangeUserDataRequest)
    fun getUserData(user: User): UserDataResponse

}