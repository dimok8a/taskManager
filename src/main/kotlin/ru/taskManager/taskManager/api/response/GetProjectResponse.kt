package ru.taskManager.taskManager.api.response

import com.fasterxml.jackson.annotation.JsonProperty
import ru.taskManager.taskManager.entity.project.Board
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.project.Section
import ru.taskManager.taskManager.entity.user.User
import java.util.Date

data class UserProjectsResponse (
    @JsonProperty("projects")
    val userProjects: List<UserProjectData>
) {}

data class ProjectFullData(
    val id: Long,
    val name: String,
    val boards: List<BoardData>,
    val participants: List<ParticipantData>,
    val owner: ParticipantData,
    val description: String? = "",
    val createdAt: Date
)
{
    constructor(project: Project): this(
        project.id!!,
        project.name!!,
        project.boards!!.map { BoardData(it) },
        project.participants!!.map { ParticipantData(it) },
        ParticipantData(project.owner),
        project.description,
        project.createdAt
        )
}

data class UserProjectData(
    val id: Long,
    val name: String,
    val description: String? = ""
){
    constructor(project: Project): this(project.id!!, project.name!!, project.description)
}

data class BoardData(
    val id: Long,
    val name: String,
    val sections: List<SectionData>,
    val description: String? = ""
){
    constructor(board: Board): this(
        board.id!!,
        board.name!!,
        board.sections!!.map { SectionData(it) },
        board.description
    )
}

data class SectionData (
    val id: Long,
    val type: String,
){
    constructor(section: Section): this(section.id!!, section.type.typeString)
}

data class ParticipantData(
    val id: Long,
    val nickname: String,
    val firstname: String,
    val lastname: String
)
{
    constructor(user: User): this(user.id!!, user.nickname, user.firstname!!, user.lastname!!)
}