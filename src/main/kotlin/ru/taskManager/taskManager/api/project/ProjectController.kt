package ru.taskManager.taskManager.api.project

import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
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

    @GetMapping("/")
    fun getProjects(): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated){
                val userDetails = authentication.principal as User
                val allProjects =  service.getAllUserProjects(userDetails)
                val allProjectsData = allProjects.map { convertToUserProjectData(it) }
                return ResponseEntity.ok(UserProjectsResponse(allProjectsData))
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Error) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    private fun convertToUserProjectData(project: Project): UserProjectData {
        return UserProjectData(
            project.id!!,
            project.name!!
        )
    }

    @GetMapping("/{id}")
    fun getFullProjectInformation(@PathVariable id: Long): ResponseEntity<*> {
        try {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication != null && authentication.isAuthenticated){
                val userDetails = authentication.principal as User
                val foundProject = service.getProjectByUserAndProjectId(userDetails, id)
                if (foundProject == null) return ResponseEntity.status(404).body(ErrorResponse("У пользователя не нашлось указанного проекта"))
                val foundProjectData = convertToFullProjectData(foundProject)
                return ResponseEntity.ok(foundProjectData)
            }
            return ResponseEntity.status(401).body(ErrorResponse("Не авторизованный пользователь"))
        } catch (e: Error) {
            return ResponseEntity.status(500).body(ErrorResponse("Что-то пошло не так"))
        }
    }

    private fun convertToFullProjectData(project: Project): ProjectFullData {
        val allBoardData = project.boards!!.map { convertToBoardData(it) }
        val allParticipantData = project.participants!!.map { convertToParticipantData(it) }
        return ProjectFullData(
            project.id!!,
            project.name!!,
            allBoardData,
            allParticipantData,
            convertToParticipantData(project.owner),
            project.description,
            project.createdAt
            )
    }

    private fun convertToBoardData(board: Board): BoardData {
        val allSectionData = board.sections!!.map { convertToSectionData(it) }
        return BoardData(board.id!!, board.name!!, allSectionData)
    }
    private fun convertToSectionData(section: Section): SectionData {
        return SectionData(section.id!!, section.type.typeString)
    }

    private fun convertToParticipantData(participant: User): ParticipantData {
        return ParticipantData(participant.id!!, participant.nickname, participant.firstname!!, participant.lastname!!)
    }


}