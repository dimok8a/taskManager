package ru.taskManager.taskManager.service

import ru.taskManager.taskManager.entity.project.Board
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.BoardRepository
import ru.taskManager.taskManager.repository.SectionRepository

interface SectionService {
    val repository: SectionRepository
    fun getSectionByUserAndBoardId(user: User, boardId: Long) : Board?
}