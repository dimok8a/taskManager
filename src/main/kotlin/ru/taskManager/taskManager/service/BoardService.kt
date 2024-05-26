package ru.taskManager.taskManager.service

import ru.taskManager.taskManager.api.request.project.ChangeBoardRequest
import ru.taskManager.taskManager.api.request.project.NewBoardRequest
import ru.taskManager.taskManager.entity.project.Board
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.project.Section
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.BoardRepository

interface BoardService {
    val repository: BoardRepository
    fun getBoardByUserAndBoardId(user: User, boardId: Long): Board?
    fun createNewBoard(request: NewBoardRequest, user: User): Board
    fun createDefaultBoard(project: Project): Board
    fun changeBoardData(request: ChangeBoardRequest, user: User, board: Board): Board
    fun deleteBoard(user: User, boardId: Long)

    fun getFirstSection(board: Board): Section?
    fun getNextSection(section: Section, board: Board): Section?
    fun getPrevSection(section: Section, board: Board): Section?
}