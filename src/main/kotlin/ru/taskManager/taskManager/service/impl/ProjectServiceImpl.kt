package ru.taskManager.taskManager.service.impl

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.taskManager.taskManager.api.request.project.AddUserToProjectRequest
import ru.taskManager.taskManager.api.request.project.ChangeProjectRequest
import ru.taskManager.taskManager.api.request.project.NewProjectRequest
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.ProjectRepository
import ru.taskManager.taskManager.repository.TaskRepository
import ru.taskManager.taskManager.service.BoardService
import ru.taskManager.taskManager.service.ProjectService
import ru.taskManager.taskManager.service.ProjectValidationService
import ru.taskManager.taskManager.service.UserService
import java.util.*

@Service
class ProjectServiceImpl(
    override val repository: ProjectRepository,
    private val taskRepository: TaskRepository,
    private val boardService: BoardService,
    private val projectValidationService: ProjectValidationService,
    private val userService: UserService
) : ProjectService {
    override fun getAllUserProjects(user: User): List<Project> {
        return repository.findAllByParticipantsIn(mutableSetOf(user))
    }

    override fun getProjectByUserAndProjectId(user: User, projectId: Long): Project? {
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
        repository.save(newProject)
        boardService.createDefaultBoard(newProject)
        return newProject
    }

    override fun changeProjectData(request: ChangeProjectRequest, user: User, project: Project): Project {
        if (request.name != null)
            project.name = request.name
        if (request.description != null)
            project.description = request.description
        repository.save(project)
        return project
    }

    override fun getProjectById(projectId: Long): Project? {
        return repository.findByIdOrNull(projectId)
    }

    override fun deleteProject(user: User, projectId: Long) {
        val project = getProjectById(projectId) ?: throw Exception("Project id was not transmitted!")
        if (projectValidationService.checkIfUserHasProject(user, project))
            return repository.deleteById(projectId)
        throw Exception("У указанного пользователя нет переданного проекта")
    }

    override fun addUserToProject(request: AddUserToProjectRequest, user: User, project: Project) {
        val newUser = userService.getUserById(request.newUserId)?: throw Exception("Не удалось найти приглашаемого пользователя")
        if (projectValidationService.checkIfUserHasProject(newUser, project)) throw Exception("Приглашаемый пользователь уже в проекте")
        if (!projectValidationService.checkIfUserHasProject(user, project)) throw Exception("Пользователь сам не состоит в указанном проекте")
        project.participants.add(newUser)
        repository.save(project)
    }

}