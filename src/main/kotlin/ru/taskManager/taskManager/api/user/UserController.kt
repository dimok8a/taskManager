package ru.taskManager.taskManager.api.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.taskManager.taskManager.api.UserRequest
import ru.taskManager.taskManager.entity.User
import ru.taskManager.taskManager.service.UserService


@RestController
@RequestMapping("/api/user")
class UserController (
    private val service: UserService
){

    @GetMapping("/")
    fun getUsers(): List<User> {
        print("getUsers")
        return service.getAllUsers()
    }

    @GetMapping("/hello")
    fun testFun(): List<User> {
        print("getUsers")
        return service.getAllUsers()
    }

    @GetMapping("/login")
    fun loginUser(): List<User> {
        return service.getAllUsers()
    }
}