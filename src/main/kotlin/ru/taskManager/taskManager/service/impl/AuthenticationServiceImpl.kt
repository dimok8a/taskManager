package ru.taskManager.taskManager.service.impl

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import ru.taskManager.taskManager.api.request.user.ChangeUserDataRequest
import ru.taskManager.taskManager.api.request.user.LoginRequest
import ru.taskManager.taskManager.api.request.user.RegisterRequest
import ru.taskManager.taskManager.api.response.AuthenticationResponse
import ru.taskManager.taskManager.api.response.UserDataResponse
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.UserRepository
import ru.taskManager.taskManager.service.AuthenticationService
import ru.taskManager.taskManager.service.JwtService
import java.math.BigInteger
import java.security.MessageDigest

@Service
class AuthenticationServiceImpl(
    override val authManager: AuthenticationManager,
    override val repository: UserRepository,
    override val jwtService: JwtService
) : AuthenticationService {
    override fun authenticate(loginRequest: LoginRequest): AuthenticationResponse {
        val user = repository.findByNickname(loginRequest.nickname).orElseThrow()
        if (md5(user.hash + loginRequest.rnd) == loginRequest.hash) {
            return AuthenticationResponse(refreshUserToken(user), user.id)
        }
        throw Exception("Неверное имя пользователя или пароль")
    }

    override fun register(registerRequest: RegisterRequest): AuthenticationResponse {
        val foundUser = repository.findByNickname(registerRequest.nickname).orElse(null)
        if (foundUser !== null) throw Exception("Пользователь с переданным никнеймом уже существует")
        val newUser =
            User(registerRequest.nickname, registerRequest.firstname, registerRequest.lastname, registerRequest.hash)
        return AuthenticationResponse(refreshUserToken(newUser), newUser.id)
    }

    override fun changeUserData(
        user: User,
        changeUserDataRequest: ChangeUserDataRequest
    ) {
        val foundUser = repository.findByNickname(user.nickname).orElseThrow()
        if (
            changeUserDataRequest.firstname != null && changeUserDataRequest.firstname != ""
            ) {
            foundUser.firstname = changeUserDataRequest.firstname
        }
        if (changeUserDataRequest.lastname != null && changeUserDataRequest.lastname != "")
        {
            foundUser.lastname = changeUserDataRequest.lastname
        }
        repository.save(foundUser)
    }

    override fun getUserData(user: User): UserDataResponse {
        val foundUser = repository.findByNickname(user.nickname).orElseThrow()
        return  UserDataResponse(foundUser)
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    private fun refreshUserToken(user: User): String {
        val newToken = jwtService.generateToken(user)
        user.token = newToken
        repository.save(user)
        return newToken
    }

}