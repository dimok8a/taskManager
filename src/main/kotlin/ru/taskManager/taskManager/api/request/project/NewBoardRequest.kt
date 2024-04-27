package ru.taskManager.taskManager.api.request.project

import com.fasterxml.jackson.annotation.JsonProperty

data class NewBoardRequest (
    @JsonProperty(value = "project_id")
    val projectId: Long,
    val name: String,
    val description: String = "",
    @JsonProperty(value = "inspector_id")
    val inspectorId: Long?,
)
{}