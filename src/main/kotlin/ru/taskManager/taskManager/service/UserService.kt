package ru.taskManager.taskManager.service

import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.UserRepository

interface UserService {
    val repository: UserRepository
    fun getAllUsers(): List<User>
    fun getUserByNickName(nickname: String): User
    fun getUserById(id: Long): User?
}