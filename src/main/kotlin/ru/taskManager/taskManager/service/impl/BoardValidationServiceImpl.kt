package ru.taskManager.taskManager.service.impl

import org.springframework.stereotype.Service
import ru.taskManager.taskManager.entity.project.Board
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.BoardRepository
import ru.taskManager.taskManager.service.BoardValidationService
import ru.taskManager.taskManager.service.ProjectValidationService

@Service
class BoardValidationServiceImpl (
    override val repository: BoardRepository,
    private val projectValidationService: ProjectValidationService
) : BoardValidationService {
    override fun checkIfUserHasBoard(user: User, board: Board): Boolean {
        val project = board.project
        return projectValidationService.checkIfUserHasProject(user, project)
    }
}