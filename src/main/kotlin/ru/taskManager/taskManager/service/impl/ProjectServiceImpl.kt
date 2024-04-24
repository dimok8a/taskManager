package ru.taskManager.taskManager.service.impl

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.taskManager.taskManager.entity.project.Board
import ru.taskManager.taskManager.entity.project.ESectionType
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.project.Section
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.BoardRepository
import ru.taskManager.taskManager.repository.ProjectRepository
import ru.taskManager.taskManager.repository.SectionRepository
import ru.taskManager.taskManager.repository.UserRepository
import ru.taskManager.taskManager.service.ProjectService
import java.util.*

@Service
class ProjectServiceImpl(
    override val repository: ProjectRepository,
    val userRepository: UserRepository,
    val boardRepository: BoardRepository,
    val sectionRepository: SectionRepository
) : ProjectService {
    override fun getAllUserProjects(user: User): List<Project> {
        return repository.findAllByParticipantsIn(mutableSetOf(user))
    }

    fun getProjectByUserAndProjectId(user: User, projectId: Long): Project {
        return repository.findByParticipantsInAndId(mutableSetOf(user), projectId).orElse(null)
    }

    @Transactional
    override fun createNewProject(): Project {
        val owner = userRepository.findById(1).get()
        val newProject = Project(
            name = "Проект",
            owner = owner,
            participants = mutableSetOf(owner),
            createdAt = Date(),
        )
        val newBoard = Board(name = "Доска", project = newProject)
        boardRepository.save(newBoard)
        for (sectionType in ESectionType.entries)
            sectionRepository.save(Section(type = sectionType, board = newBoard))
        newProject.boards = mutableListOf(newBoard)
        return repository.save(newProject)
    }
}