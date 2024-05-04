package ru.taskManager.taskManager

import jakarta.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.taskManager.taskManager.api.request.project.NewProjectRequest
import ru.taskManager.taskManager.api.response.UserProjectsResponse
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.ProjectRepository
import ru.taskManager.taskManager.repository.UserRepository
import ru.taskManager.taskManager.service.JwtService
import ru.taskManager.taskManager.service.ProjectService


@ComponentScan(basePackages = ["ru.taskManager"])
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProjectApiTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var jwtService: JwtService

    @Autowired
    private lateinit var projectService: ProjectService

    @Test
    fun `test getProjects without authentication`() {
        mockMvc.get("/api/project/").andExpect { status { isUnauthorized() } }
    }

    @Test
    fun `test getProjects with authentication`() {
        val user = createTestUser()
        createTestProject(user)
        val request = MockMvcRequestBuilders
            .get("/api/project/")
            .header("Authorization", "Bearer ${user.token}")

        val response = mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
            .convert<UserProjectsResponse>()

        assertEquals(1, response.userProjects.size)
        val userProject = response.userProjects.first()
        assertEquals("Тест проект", userProject.name)
        assertEquals("Тест описание", userProject.description)
    }

    private fun createTestUser(): User {
        val testUser = User("testUser", "Test", "User", "202cb962ac59075b964b07152d234b70")
        testUser.token = jwtService.generateToken(testUser)
        return userRepository.save(testUser)
    }

    private fun createTestProject(user: User): Project {
        val projectRequest = NewProjectRequest("Тест проект", "Тест описание")
        return projectService.createNewProject(projectRequest, user)
    }

}