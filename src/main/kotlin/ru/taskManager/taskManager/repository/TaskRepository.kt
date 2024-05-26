package ru.taskManager.taskManager.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.taskManager.taskManager.entity.project.Task

interface TaskRepository : JpaRepository<Task, Long>