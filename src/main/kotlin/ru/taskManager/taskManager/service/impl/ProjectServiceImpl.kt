package ru.taskManager.taskManager.service.impl

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.taskManager.taskManager.api.request.NewProjectRequest
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

    fun getProjectByUserAndProjectId(user: User, projectId: Long): Project? {
        return repository.findByParticipantsInAndId(mutableSetOf(user), projectId).orElse(null)
    }

    @Transactional
    override fun createNewProject(request: NewProjectRequest, user: User): Project {
        val newProject = Project(
            name = request.name,
            owner = user,
            participants = mutableSetOf(user),
            createdAt = Date(),
            description = request.description,
        )
        val newBoard = Board(project = newProject, sections = mutableListOf())
        for (sectionType in ESectionType.entries)
        {
            val newSection = Section(type = sectionType, board = newBoard)
            sectionRepository.save(newSection)
            newBoard.sections!!.add(newSection)
        }
        newProject.boards = mutableListOf(newBoard)
        repository.save(newProject)
        boardRepository.save(newBoard)
        return newProject
    }
}