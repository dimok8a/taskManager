package ru.taskManager.taskManager.api.response

import com.fasterxml.jackson.annotation.JsonProperty
import ru.taskManager.taskManager.entity.project.Board
import ru.taskManager.taskManager.entity.project.Project
import ru.taskManager.taskManager.entity.project.Section
import ru.taskManager.taskManager.entity.project.Task
import ru.taskManager.taskManager.entity.user.User
import java.util.*

data class UserProjectsResponse(
    val userProjects: List<ProjectShortData>
)

data class ProjectShortData(
    val id: Long,
    val name: String,
    val description: String = ""
) {
    constructor(project: Project) : this(
        project.id ?: throw NullPointerException("Project id is null"),
        project.name ?: throw NullPointerException("Project name is null"),
        project.description ?: ""
    )
}

data class ProjectData(
    val id: Long,
    val name: String,
    val currentBoard: BoardData?,
    val boards: List<BoardShortData>?,
    val participants: List<ParticipantData>,
    val owner: ParticipantData,
    val description: String = "",
    val createdAt: Date
) {
    constructor(project: Project) : this(
        project.id ?: throw NullPointerException("Project id is null"),
        project.name ?: throw NullPointerException("Project name is null"),
        project.boards.firstOrNull()?.let { BoardData(it) },
//        project.boards.ifEmpty { null }?.let { arr -> arr.map { BoardShortData(it) } },
        project.boards.map { BoardShortData(it) },
        project.participants.map { ParticipantData(it) },
        ParticipantData(project.owner),
        project.description ?: "",
        project.createdAt
    )
}


data class BoardData(
    val id: Long,
    val name: String,
    val sections: List<SectionData>,
    val description: String = ""
) {
    constructor(board: Board) : this(
        board.id ?: throw NullPointerException("Board id is null"),
        board.name ?: throw NullPointerException("Board name is null"),
        board.sections.map { SectionData(it) },
        board.description ?: ""
    )
}

data class BoardShortData(
    val id: Long,
    val name: String,
) {
    constructor(board: Board) : this(
        board.id ?: throw NullPointerException("Board id is null"),
        board.name ?: throw NullPointerException("Board name is null"),
    )
}

data class SectionData(
    val id: Long,
    val type: String,
    val tasks: List<TaskShortData>
) {
    constructor(section: Section) : this(
        section.id ?: throw NullPointerException("Section id is null"),
        section.type.typeString,
        section.tasks.map { TaskShortData(it) }
    )
}

data class SectionShortData(
    val id: Long,
    val type: String
) {
    constructor(section: Section) : this(
        section.id ?: throw NullPointerException("Section id is null"),
        section.type.typeString,
    )
}

data class TaskData(
    val id: Long,
    val name: String,
    val description: String = "",
    val createdAt: Date,
    val executor: ParticipantData?,
    val inspector: ParticipantData?,
    val section: SectionShortData
) {
    constructor(task: Task) : this(
        task.id ?: throw NullPointerException("Task id is null"),
        task.name,
        task.description ?: "",
        task.createdAt,
        task.executor?.let { ParticipantData(it) },
        task.inspector?.let { ParticipantData(it) },
        SectionShortData(task.section)
    )
}

data class UserTasksResponse(
    val tasks: List<TaskShortData>
)

data class TaskShortData(
    val id: Long,
    val name: String,
) {
    constructor(task: Task) : this(
        task.id!!,
        task.name
    )
}

data class ParticipantData(
    val id: Long,
    val nickname: String,
    val firstname: String,
    val lastname: String
) {
    constructor(user: User) : this(user.id!!, user.nickname, user.firstname!!, user.lastname!!)
}