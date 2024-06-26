package ru.taskManager.taskManager.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.UserRepository
import ru.taskManager.taskManager.service.UserService

@Service
class UserServiceImpl(
    override val repository: UserRepository,
) : UserService {

    override fun getAllUsers(): List<User> {
        return repository.findAll()
    }

    override fun getUserByNickName(nickname: String): User {
        return repository.findByNickname(nickname).get()
    }

    override fun getUserById(id: Long): User? {
        return repository.findByIdOrNull(id)
    }


//    fun authenticate(user: User): AuthenticationResponse {
//
//    }

}