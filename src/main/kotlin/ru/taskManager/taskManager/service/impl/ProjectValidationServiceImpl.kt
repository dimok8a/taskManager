package ru.taskManager.taskManager.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.ProjectRepository
import ru.taskManager.taskManager.service.ProjectValidationService

@Service
class ProjectValidationServiceImpl(override val repository: ProjectRepository) : ProjectValidationService {
    override fun checkIfUserHasProject(user: User, project: Project): Boolean {
        return repository.findByParticipantsInAndId(mutableSetOf(user), project.id?:throw Exception("Project id was not transmitted")).isPresent
    }

    override fun getProjectById(projectId: Long): Project? {
        return repository.findByIdOrNull(projectId)
    }
}