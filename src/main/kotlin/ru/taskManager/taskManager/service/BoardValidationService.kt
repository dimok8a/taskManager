package ru.taskManager.taskManager.service

import ru.taskManager.taskManager.entity.project.Board
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.BoardRepository

interface BoardValidationService {
    val repository: BoardRepository
    fun checkIfUserHasBoard(user: User, board: Board): Boolean
}