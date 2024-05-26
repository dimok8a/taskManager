package ru.taskManager.taskManager.api.request.project

data class NewBoardRequest(
    val projectId: Long,
    val name: String,
    val description: String = "",
    val inspectorId: Long?,
)