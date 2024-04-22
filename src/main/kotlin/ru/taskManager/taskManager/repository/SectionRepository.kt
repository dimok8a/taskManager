package ru.taskManager.taskManager.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.taskManager.taskManager.entity.Section
import ru.taskManager.taskManager.entity.User

interface SectionRepository : JpaRepository<Section, Long> {
}