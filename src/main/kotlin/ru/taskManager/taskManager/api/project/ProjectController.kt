package ru.taskManager.taskManager.api.project

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import ru.taskManager.taskManager.api.request.LoginRequest
import ru.taskManager.taskManager.api.request.NewProjectRequest
import ru.taskManager.taskManager.api.response.*
import ru.taskManager.taskManager.entity.project.Board
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.project.Section
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.service.impl.ProjectServiceImpl

@RestController
@RequestMapping("/api/project")
class ProjectController (
    private val service: ProjectServiceImpl
) {


    // Get all user projects
    @GetMapping("/")
    fun getProjects(): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated){
                val userDetails = authentication.principal as User
                val allProjects =  service.getAllUserProjects(userDetails)
                val allProjectsData = allProjects.map { UserProjectData(it) }
                return ResponseEntity.ok(UserProjectsResponse(allProjectsData))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Error) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    // Get user's project by project's id
    @GetMapping("/{id}")
    fun getFullProjectInformation(@PathVariable id: Long): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated){
                val userDetails = authentication.principal as User
                val foundProject = service.getProjectByUserAndProjectId(userDetails, id)
                if (foundProject == null) return ResponseEntity.status(404).body(ErrorResponse("У пользователя не нашлось указанного проекта"))
                return ResponseEntity.ok(ProjectFullData(foundProject))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Error) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    // Create new project
    @PostMapping("/")
    fun createNewProject(
        @RequestBody
        request: NewProjectRequest
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated){
                val userDetails = authentication.principal as User
                val newProject = service.createNewProject(request, userDetails)
                return ResponseEntity.ok().body(ProjectFullData(newProject))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Error) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }


}