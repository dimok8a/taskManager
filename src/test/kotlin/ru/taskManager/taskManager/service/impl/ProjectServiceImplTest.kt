package ru.taskManager.taskManager.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.ProjectRepository
import ru.taskManager.taskManager.repository.TaskRepository
import ru.taskManager.taskManager.service.BoardService
import ru.taskManager.taskManager.service.ProjectValidationService
import ru.taskManager.taskManager.service.UserService
import java.util.*

class ProjectServiceImplTest {

    private val projectRepository = mock<ProjectRepository>()
    private val taskRepository = mock<TaskRepository>()
    private val boardService = mock<BoardService>()
    private val projectValidationService = mock<ProjectValidationService>()
    private val userService = mock<UserService>()

    private val projectService = ProjectServiceImpl(
        projectRepository, taskRepository, boardService, projectValidationService, userService
    )

    @Test
    fun `test getAllUserProjects`() {
        // Objects
        val user = User("test nickname").apply {
            id = 1
        }
        val projects = listOf(
            Project(
                id = 2,
                createdAt = Date(1L),
                owner = user
            ),
            Project(
                id = 3,
                createdAt = Date(1L),
                owner = user
            )
        )

        // Mocking
        whenever(projectRepository.findAllByParticipantsIn(mutableSetOf(user))) doReturn projects

        // Execution
        val result = projectService.getAllUserProjects(user)

        // Verification
        assertEquals(projects, result)
        verify(projectRepository).findAllByParticipantsIn(any())
    }

}