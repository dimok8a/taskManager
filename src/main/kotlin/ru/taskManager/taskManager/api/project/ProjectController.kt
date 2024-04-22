package ru.taskManager.taskManager.api.project

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.taskManager.taskManager.api.UserRequest
import ru.taskManager.taskManager.entity.Project
import ru.taskManager.taskManager.service.ProjectService

@RestController
@RequestMapping("/api/project")
class ProjectController (
    private val service: ProjectService
) {

    @GetMapping
    fun getProjects(@RequestBody request: UserRequest): List<Project> {
        return service.getAllProjects()
    }
}