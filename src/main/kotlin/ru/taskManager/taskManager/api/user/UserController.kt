package ru.taskManager.taskManager.api.user

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import ru.taskManager.taskManager.api.request.user.ChangeUserDataRequest
import ru.taskManager.taskManager.api.request.user.LoginRequest
import ru.taskManager.taskManager.api.request.user.RegisterRequest
import ru.taskManager.taskManager.api.response.ErrorResponse
import ru.taskManager.taskManager.api.response.TaskData
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

    @PutMapping("/")
    fun changeUserData(
        @RequestBody
        request: ChangeUserDataRequest
    ): ResponseEntity<*> {
        return try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                if (!request.isEmpty()) {
                    val userDetails = authentication.principal as User
                    return ResponseEntity.ok().body(authenticationService.changeUserData(userDetails, request))
                }
                return ResponseEntity.status(400).body(ErrorResponse("Пустое тело запроса"))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            ResponseEntity.status(401).body(ErrorResponse(e.message))
        }
    }

    @GetMapping("/")
    fun getUserData(
    ): ResponseEntity<*> {
        return try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                return ResponseEntity.ok().body(authenticationService.getUserData(userDetails))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
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
