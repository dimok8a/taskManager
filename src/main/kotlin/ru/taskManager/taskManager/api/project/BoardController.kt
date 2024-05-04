package ru.taskManager.taskManager.api.project

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import ru.taskManager.taskManager.api.request.project.ChangeBoardRequest
import ru.taskManager.taskManager.api.request.project.NewBoardRequest
import ru.taskManager.taskManager.api.response.BoardData
import ru.taskManager.taskManager.api.response.ErrorResponse
import ru.taskManager.taskManager.api.response.SuccessResponse
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.service.BoardService

@RestController
@RequestMapping("/api/board")
class BoardController(
    val service: BoardService
) {
    @GetMapping("/{id}")
    fun getFullBoardInformation(@PathVariable id: Long): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val foundBoard = service.getBoardByUserAndBoardId(userDetails, id)
                    ?: return ResponseEntity.status(404)
                        .body(ErrorResponse("У пользователя не нашлось указанной доски"))
                return ResponseEntity.ok(BoardData(foundBoard))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Error) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @PostMapping("/")
    fun createNewBoard(
        @RequestBody
        request: NewBoardRequest
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                val newBoard = service.createNewBoard(request, userDetails)
                return ResponseEntity.ok().body(BoardData(newBoard))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Error) {
            return ResponseEntity.status(500).body(ErrorResponse(e.message))
        }
    }

    @PutMapping("/{id}")
    fun changeBoardData(
        @PathVariable id: Long,
        @RequestBody
        request: ChangeBoardRequest
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                if (!request.isEmpty()) {
                    val userDetails = authentication.principal as User
                    val foundBoard = service.getBoardByUserAndBoardId(userDetails, id)
                        ?: return ResponseEntity.status(404)
                            .body(ErrorResponse("У пользователя не нашлось указанной доски"))
                    val changedBoard = service.changeBoardData(request, userDetails, foundBoard)
                    return ResponseEntity.ok().body(BoardData(changedBoard))
                }
                return ResponseEntity.status(400).body(ErrorResponse("Пустое тело запроса"))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Error) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteBoard(
        @PathVariable id: Long
    ): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated) {
                val userDetails = authentication.principal as User
                service.deleteBoard(userDetails, id)
                return ResponseEntity.ok().body(SuccessResponse("Доска была успешно удалена"))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Error) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }
}