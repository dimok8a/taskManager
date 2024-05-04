package ru.taskManager.taskManager.api.project

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import ru.taskManager.taskManager.api.request.project.ChangeTaskDataRequest
import ru.taskManager.taskManager.api.request.project.NewTaskRequest
import ru.taskManager.taskManager.api.response.*
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.service.TaskService

@RestController
@RequestMapping("/api/task")
class TaskController(
    val service: TaskService
) {

    @GetMapping("/{id}")
    fun getFullTaskInformation(@PathVariable id: Long): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val foundTask = service.getTaskByUserAndTaskId(userDetails, id)
                    ?: return ResponseEntity.status(404)
                        .body(ErrorResponse("У пользователя не нашлось указанной задачи"))
                return ResponseEntity.ok(TaskData(foundTask))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @GetMapping("/todo")
    fun getExecutorTasks(): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val allTasks = service.getAllExecutorTasks(userDetails)
                val allTasksData = allTasks.map { TaskShortData(it) }
                return ResponseEntity.ok(UserTasksResponse(allTasksData))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @GetMapping("/toCheck")
    fun getInspectorTasks(): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val allTasks = service.getAllInspectorTasks(userDetails)
                val allTasksData = allTasks.map { TaskShortData(it) }
                return ResponseEntity.ok(UserTasksResponse(allTasksData))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @PostMapping("/")
    fun createNewTask(
        @RequestBody
        request: NewTaskRequest
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val newBoard = service.createNewTask(request, userDetails)
                return ResponseEntity.ok().body(TaskData(newBoard))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse(e.message))
        }
    }

    @PutMapping("/{id}")
    fun changeTaskData(
        @PathVariable id: Long,
        @RequestBody
        request: ChangeTaskDataRequest
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                if (!request.isEmpty()) {
                    val userDetails = authentication.principal as User
                    val foundTask = service.getTaskByUserAndTaskId(userDetails, id)
                        ?: return ResponseEntity.status(404)
                            .body(ErrorResponse("У пользователя не нашлось указанной задачи"))
                    val changedTask = service.changeTaskData(request, userDetails, foundTask)
                    return ResponseEntity.ok().body(TaskData(changedTask))
                }
                return ResponseEntity.status(400).body(ErrorResponse("Пустое тело запроса"))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @PutMapping("/take/{id}")
    fun takeTaskOnExecution(
        @PathVariable id: Long,
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val foundTask = service.getTaskByUserAndTaskId(userDetails, id)
                    ?: return ResponseEntity.status(404)
                        .body(ErrorResponse("У пользователя не нашлось указанной задачи"))
                val changedTask = service.takeTaskOnExecution(userDetails, foundTask)
                return ResponseEntity.ok().body(TaskData(changedTask))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @PutMapping("/complete/{id}")
    fun completeTask(
        @PathVariable id: Long,
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val foundTask = service.getTaskByUserAndTaskId(userDetails, id)
                    ?: return ResponseEntity.status(404)
                        .body(ErrorResponse("У пользователя не нашлось указанной задачи"))
                val changedTask = service.completeTask(userDetails, foundTask)
                return ResponseEntity.ok().body(TaskData(changedTask))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @PutMapping("/check/{id}")
    fun checkTask(
        @PathVariable id: Long,
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val foundTask = service.getTaskByUserAndTaskId(userDetails, id)
                    ?: return ResponseEntity.status(404)
                        .body(ErrorResponse("У пользователя не нашлось указанной задачи"))
                val changedTask = service.checkTask(userDetails, foundTask)
                return ResponseEntity.ok().body(TaskData(changedTask))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @PutMapping("/reject/{id}")
    fun rejectTask(
        @PathVariable id: Long,
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val foundTask = service.getTaskByUserAndTaskId(userDetails, id)
                    ?: return ResponseEntity.status(404)
                        .body(ErrorResponse("У пользователя не нашлось указанной задачи"))
                val changedTask = service.rejectTask(userDetails, foundTask)
                return ResponseEntity.ok().body(TaskData(changedTask))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Exception) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }


    @DeleteMapping("/{id}")
    fun deleteTask(
        @PathVariable id: Long
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                service.deleteTask(userDetails, id)
                return ResponseEntity.ok().body(SuccessResponse("Задача была успешно удалена"))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Error) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }
}