package ru.taskManager.taskManager.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import ru.taskManager.taskManager.entity.Board
import ru.taskManager.taskManager.entity.ESectionType
import ru.taskManager.taskManager.entity.Project
import ru.taskManager.taskManager.entity.Section
import ru.taskManager.taskManager.repository.BoardRepository
import ru.taskManager.taskManager.repository.ProjectRepository
import ru.taskManager.taskManager.repository.SectionRepository
import ru.taskManager.taskManager.repository.UserRepository
import ru.taskManager.taskManager.service.impl.ProjectServiceImpl
import java.util.*

@Service
class ProjectService(
    override val repository: ProjectRepository,
    val userRepository: UserRepository,
    val boardRepository: BoardRepository,
    val sectionRepository: SectionRepository
) : ProjectServiceImpl {
    @Transactional
    override fun getAllProjects(): MutableList<Project> {
        return  repository.findAll()
    }

    @Transactional
    override fun createNewProject(): Project {
        val owner = userRepository.findById(1).get()
        val newProject = Project(
            name = "Проект",
            owner = owner,
            participants = mutableSetOf(owner),
            createdAt = Date()
        )
        val newBoard = Board(name = "Доска", project = newProject)
        boardRepository.save(newBoard)
        for (sectionType in ESectionType.entries)
            sectionRepository.save(Section(type = sectionType, board = newBoard))
        return repository.save(newProject)
    }
}