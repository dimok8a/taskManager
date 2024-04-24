package ru.taskManager.taskManager.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.user.User
import java.util.*

interface ProjectRepository: JpaRepository<Project, Long> {
    fun findAllByParticipantsIn(participants: MutableSet<User>): List<Project>

    fun findByParticipantsInAndId(participants: MutableSet<User>, projectId: Long): Optional<Project>
}