package ru.taskManager.taskManager

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.taskManager.taskManager.api.request.project.NewProjectRequest
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.UserRepository
import ru.taskManager.taskManager.service.JwtService
import ru.taskManager.taskManager.service.ProjectService
import java.util.Optional


@ComponentScan(basePackages = ["ru.taskManager"])
@SpringBootTest
@AutoConfigureMockMvc
class ProjectApiTests (

) {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userRepository: UserRepository
    @MockBean
    lateinit var jwtService: JwtService
    @MockBean
    lateinit var projectService: ProjectService

    private lateinit var testUser: User
    private lateinit var testProject: Project

    @Test
    fun `test getProjects without authentication`() {
        val result = mockMvc.get("/api/project/").andExpect {status { isUnauthorized() }}
    }

    fun createTestUser() {
        testUser = User("testUser", "Test", "User", "202cb962ac59075b964b07152d234b70")
        testUser.token = jwtService.generateToken(testUser)
        Mockito.`when`(userRepository.save(testUser)).thenReturn(testUser)
    }

    fun createTestProject() {
        val projectRequest = NewProjectRequest("Тест проект")
        projectService.createNewProject(projectRequest, testUser)
    }

    @Test
    fun `test getProjects with authentication`() {
        createTestUser()
        createTestProject()
        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/project/")
                .header("Authorization", "Bearer ${testUser.token}"))
        println("Bearer ${testUser.token}")
        result.andExpect(MockMvcResultMatchers.status().isOk)
    }


}