package ru.taskManager.taskManager.service.impl

import ru.taskManager.taskManager.entity.Project
import ru.taskManager.taskManager.repository.ProjectRepository

interface ProjectServiceImpl {
    val repository: ProjectRepository
    fun getAllProjects() : MutableList<Project>
    fun createNewProject() : Project
}