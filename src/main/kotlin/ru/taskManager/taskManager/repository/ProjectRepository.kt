package ru.taskManager.taskManager.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.taskManager.taskManager.entity.Project

interface ProjectRepository: JpaRepository<Project, Long> {
}