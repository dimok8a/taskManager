package ru.taskManager.taskManager.api.response

import com.fasterxml.jackson.annotation.JsonProperty
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
){}

data class UserProjectData(
    val id: Long,
    val name: String
){}

data class BoardData(
    val id: Long,
    val name: String,
    val sections: List<SectionData>
){}

data class SectionData (
    val id: Long,
    val type: String
){}

data class ParticipantData(
    val id: Long,
    val nickname: String,
    val firstname: String,
    val lastname: String
) {}