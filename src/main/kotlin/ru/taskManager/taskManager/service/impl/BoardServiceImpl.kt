package ru.taskManager.taskManager.service.impl

import org.springframework.stereotype.Service
import ru.taskManager.taskManager.api.request.project.ChangeBoardRequest
import ru.taskManager.taskManager.api.request.project.NewBoardRequest
import ru.taskManager.taskManager.entity.project.Board
import ru.taskManager.taskManager.entity.project.ESectionType
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.project.Section
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.BoardRepository
import ru.taskManager.taskManager.repository.SectionRepository
import ru.taskManager.taskManager.service.BoardService
import ru.taskManager.taskManager.service.BoardValidationService
import ru.taskManager.taskManager.service.ProjectValidationService
import ru.taskManager.taskManager.service.UserService

@Service
class BoardServiceImpl(
    override val repository: BoardRepository,
    val userService: UserService,
    private val sectionRepository: SectionRepository,
    private val projectValidationService: ProjectValidationService,
    private val boardValidationService: BoardValidationService,
) : BoardService {


    override fun getBoardByUserAndBoardId(user: User, boardId: Long): Board? {
        val board = repository.findById(boardId).orElse(null) ?: return null
        if (boardValidationService.checkIfUserHasBoard(user, board)) return board
        return null
    }

    override fun createNewBoard(request: NewBoardRequest, user: User): Board {
        val project =
            projectValidationService.getProjectById(request.projectId) ?: throw Exception("Не удалось найти проект")
        if (!projectValidationService.checkIfUserHasProject(
                user,
                project
            )
        ) throw Error("Не удалось найти проект у пользователя")
        var inspector = request.inspectorId?.let { userService.getUserById(it) }
        if (inspector != null && !projectValidationService.checkIfUserHasProject(inspector, project)) inspector = null
        val board = Board(
            name = request.name,
            description = request.description,
            project = project,
            inspectorByDefault = inspector
        )
        repository.save(board)
        for (sectionType in ESectionType.entries)
            board.sections.add(sectionRepository.save(Section(type = sectionType, board = board)))
        return board
    }

    override fun createDefaultBoard(project: Project): Board {
        val newBoard = Board(project = project)
        // TODO: this logic should be in a section service
        for (sectionType in ESectionType.entries)
            newBoard.sections.add(sectionRepository.save(Section(type = sectionType, board = newBoard)))
        return repository.save(newBoard)
    }

    override fun changeBoardData(request: ChangeBoardRequest, user: User, board: Board): Board {
        if (!boardValidationService.checkIfUserHasBoard(
                user,
                board
            )
        ) throw Exception("Не удалось найти доску у пользователя")
        if (request.name != null) board.name = request.name
        if (request.description != null) board.description = request.description
        val inspector = request.inspectorId?.let { userService.getUserById(it) }
        if (inspector != null && boardValidationService.checkIfUserHasBoard(inspector, board))
            board.inspectorByDefault = inspector
        repository.save(board)
        return board
    }

    override fun deleteBoard(user: User, boardId: Long) {
        val board = getBoardByUserAndBoardId(user, boardId) ?: throw Exception("Не удалось найти доску у пользователя")
        repository.delete(board)
    }

    override fun getFirstSection(board: Board): Section? {
        return board.sections.firstOrNull()
    }

    override fun getNextSection(section: Section, board: Board): Section? {
        val sections = board.sections
        for (i in 0..<sections.size-1) {
            if (sections[i] == section)
                return sections[i + 1]
        }
        return null
    }

    override fun getPrevSection(section: Section, board: Board): Section? {
        val sections = board.sections
        for (i in 1..<sections.size) {
            if (sections[i] == section)
                return sections[i - 1]
        }
        return null
    }


}