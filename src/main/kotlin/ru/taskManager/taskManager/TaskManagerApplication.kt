package ru.taskManager.taskManager

import org.hibernate.SessionFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.server.Session
import org.springframework.context.annotation.Bean
import ru.taskManager.taskManager.api.request.project.NewProjectRequest
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.TaskRepository
import ru.taskManager.taskManager.repository.UserRepository
import ru.taskManager.taskManager.service.JwtService
import ru.taskManager.taskManager.service.ProjectService
import ru.taskManager.taskManager.service.impl.ProjectServiceImpl
import java.util.*

@SpringBootApplication
class TaskManagerApplication {
	@Bean
	fun commandLineRunner(
		service: UserRepository,
		projectService: ProjectService,
		jwtService: JwtService,
		taskRepository: TaskRepository,
	): CommandLineRunner {
		return CommandLineRunner {
				args: Array<String?>? ->
			val firstUser = User("Dima", "Dmitry", "Okishev", "202cb962ac59075b964b07152d234b70")
			firstUser.token = jwtService.generateToken(firstUser)
			service.save(firstUser)
			val projectRequest = NewProjectRequest("Проект 1", "Описание проекта")
			projectService.createNewProject(projectRequest, firstUser)

			println(firstUser.token)
		}
	}
}


fun main(args: Array<String>) {
	runApplication<TaskManagerApplication>(*args)
}
