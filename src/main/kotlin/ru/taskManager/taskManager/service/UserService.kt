package ru.taskManager.taskManager.service

import org.springframework.stereotype.Service
import ru.taskManager.taskManager.entity.User
import ru.taskManager.taskManager.repository.UserRepository
import ru.taskManager.taskManager.service.impl.UserServiceImpl
import java.util.*

@Service
class UserService(
    override val repository: UserRepository
) : UserServiceImpl {
    override fun getAllUsers(): List<User> {
        return repository.findAll()
    }

    override fun getUserByNickName(nickname: String): User {
        return repository.findByNickname(nickname).get()
    }

}