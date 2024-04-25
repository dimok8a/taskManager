package ru.taskManager.taskManager.service

import ru.taskManager.taskManager.api.request.NewProjectRequest
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.ProjectRepository

interface ProjectService {
    val repository: ProjectRepository
    fun getAllUserProjects(user: User) : List<Project>
    fun createNewProject(request: NewProjectRequest, user: User) : Project
}