package ru.taskManager.taskManager.api.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.taskManager.taskManager.api.request.user.LoginRequest
import ru.taskManager.taskManager.api.request.user.RegisterRequest
import ru.taskManager.taskManager.api.response.ErrorResponse
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.service.AuthenticationService
import ru.taskManager.taskManager.service.UserService
import ru.taskManager.taskManager.service.impl.UserServiceImpl


@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
    private val authenticationService: AuthenticationService
) {

    @GetMapping("/")
    fun getUsers(): List<User> {
        return userService.getAllUsers()
    }

    @PostMapping("/login")
    fun loginUser(
        @RequestBody
        request: LoginRequest
    ): ResponseEntity<*> {
        return try {
            ResponseEntity.ok().body(authenticationService.authenticate(request))
        } catch (e: Exception) {
            ResponseEntity.status(401).body(ErrorResponse(e.message))
        }
    }

    @PostMapping("/register")
    fun registerUser(
        @RequestBody
        request: RegisterRequest
    ): ResponseEntity<*> {
        return try {
            ResponseEntity.ok().body(authenticationService.register(request))
        } catch (e: Exception) {
            ResponseEntity.status(401).body(ErrorResponse(e.message))
        }
    }

}