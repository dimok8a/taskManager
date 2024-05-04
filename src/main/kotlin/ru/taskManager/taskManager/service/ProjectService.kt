package ru.taskManager.taskManager.service

import ru.taskManager.taskManager.api.request.project.AddUserToProjectRequest
import ru.taskManager.taskManager.api.request.project.ChangeProjectRequest
import ru.taskManager.taskManager.api.request.project.NewProjectRequest
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.ProjectRepository

interface ProjectService {
    val repository: ProjectRepository
    fun getAllUserProjects(user: User): List<Project>
    fun createNewProject(request: NewProjectRequest, user: User): Project
    fun getProjectByUserAndProjectId(user: User, projectId: Long): Project?
    fun changeProjectData(request: ChangeProjectRequest, user: User, project: Project): Project
    fun getProjectById(projectId: Long): Project?
    fun deleteProject(user: User, projectId: Long)

    fun addUserToProject(request: AddUserToProjectRequest, user: User, project: Project)
}