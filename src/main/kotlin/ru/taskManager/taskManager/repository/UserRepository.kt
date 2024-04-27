package ru.taskManager.taskManager.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.taskManager.taskManager.entity.user.User
import java.util.Optional

interface UserRepository : JpaRepository <User, Long> {
    fun findByNickname(nickname: String): Optional<User>
    fun findByToken(token: String): Optional<User>

}