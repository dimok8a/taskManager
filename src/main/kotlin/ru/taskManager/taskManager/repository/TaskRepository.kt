package ru.taskManager.taskManager.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.taskManager.taskManager.entity.project.Task

interface TaskRepository: JpaRepository<Task, Long> {
}