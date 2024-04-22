package ru.taskManager.taskManager.service.impl

import ru.taskManager.taskManager.entity.User
import ru.taskManager.taskManager.repository.UserRepository
import java.util.Optional

interface UserServiceImpl {
    val repository: UserRepository
    fun getAllUsers() : List<User>
    fun getUserByNickName(nickname: String): User
}