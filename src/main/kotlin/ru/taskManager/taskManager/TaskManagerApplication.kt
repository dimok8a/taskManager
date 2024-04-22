package ru.taskManager.taskManager

import jakarta.transaction.Transactional
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import ru.taskManager.taskManager.entity.Role
import ru.taskManager.taskManager.entity.User
import ru.taskManager.taskManager.repository.UserRepository
import ru.taskManager.taskManager.service.JwtService
import ru.taskManager.taskManager.service.ProjectService

@SpringBootApplication
class TaskManagerApplication {
	@Bean
	fun commandLineRunner(
		service: UserRepository,
		projectService: ProjectService,
		jwtService: JwtService
	): CommandLineRunner {
		return CommandLineRunner {
				args: Array<String?>? ->
			System.out.println("sdfasdf")
			val firstUser = User(1, "Dima", "Dmitry", Role.USER, "Okishev", "sdfsadf")
			service.save(firstUser)
			val newToken = jwtService.generateToken(firstUser)
			println(newToken)
			projectService.createNewProject()
			val firstProject = projectService.getAllProjects()[0]
			val boards = firstProject.boards
			if (boards != null) {
				for (board in boards)
					println(board.name)
			}
		}
	}
}


fun main(args: Array<String>) {
	runApplication<TaskManagerApplication>(*args)
}
