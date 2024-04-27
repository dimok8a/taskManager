package ru.taskManager.taskManager.service

import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.ProjectRepository

interface ProjectValidationService {
    val repository: ProjectRepository
    fun checkIfUserHasProject(user: User, project: Project): Boolean
    fun getProjectById(projectId: Long): Project?
}