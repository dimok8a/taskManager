package ru.taskManager.taskManager.api.request.project

data class NewTaskRequest(
    val boardId: Long,
    val name: String,
    val description: String = "",
    val executorId: Long? = null,
    val inspectorId: Long? = null,
    )
