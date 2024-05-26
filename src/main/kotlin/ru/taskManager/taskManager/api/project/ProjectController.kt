package ru.taskManager.taskManager.api.project

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import ru.taskManager.taskManager.api.request.project.AddUserToProjectRequest
import ru.taskManager.taskManager.api.request.project.ChangeProjectRequest
import ru.taskManager.taskManager.api.request.project.NewProjectRequest
import ru.taskManager.taskManager.api.response.*
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.service.ProjectService

@RestController
@RequestMapping("/api/project")
class ProjectController(
    private val service: ProjectService
) {


    @GetMapping("/")
    fun getProjects(): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val allProjects = service.getAllUserProjects(userDetails)
                val allProjectsData = allProjects.map { ProjectShortData(it) }
                return ResponseEntity.ok(UserProjectsResponse(allProjectsData))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @GetMapping("/{id}")
    fun getFullProjectInformation(@PathVariable id: Long): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val foundProject = service.getProjectByUserAndProjectId(userDetails, id)
                    ?: return ResponseEntity.status(404)
                        .body(ErrorResponse("У пользователя не нашлось указанного проекта"))
                return ResponseEntity.ok(ProjectData(foundProject))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @PostMapping("/")
    fun changeProjectData(
        @RequestBody
        request: NewProjectRequest
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val newProject = service.createNewProject(request, userDetails)
                return ResponseEntity.ok().body(ProjectData(newProject))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @PutMapping("/{id}")
    fun changeProjectData(
        @PathVariable id: Long,
        @RequestBody
        request: ChangeProjectRequest
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                if (!request.isEmpty()) {
                    val userDetails = authentication.principal as User
                    val foundProject = service.getProjectByUserAndProjectId(userDetails, id)
                        ?: return ResponseEntity.status(404)
                            .body(ErrorResponse("У пользователя не нашлось указанного проекта"))
                    val changedProject = service.changeProjectData(request, userDetails, foundProject)
                    return ResponseEntity.ok().body(ProjectData(changedProject))
                }
                return ResponseEntity.status(400).body(ErrorResponse("Пустое тело запроса"))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @PutMapping("/addUser/{id}")
    fun addUser(
        @PathVariable id: Long,
        @RequestBody
        request: AddUserToProjectRequest
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                if (!request.isEmpty()) {
                    val userDetails = authentication.principal as User
                    val foundProject = service.getProjectByUserAndProjectId(userDetails, id)
                        ?: return ResponseEntity.status(404)
                            .body(ErrorResponse("У пользователя не нашлось указанного проекта"))
                    service.addUserToProject(request, userDetails, foundProject)
                    return ResponseEntity.ok().body(SuccessResponse("Пользователь был успешно добавлен"))
                }
                return ResponseEntity.status(400).body(ErrorResponse("Пустое тело запроса"))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteProject(
        @PathVariable id: Long
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                service.deleteProject(userDetails, id)
                return ResponseEntity.ok().body(SuccessResponse("Проект был успешно удален"))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }


}