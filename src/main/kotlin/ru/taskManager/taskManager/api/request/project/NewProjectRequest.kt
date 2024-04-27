package ru.taskManager.taskManager.api.request.project

data class NewProjectRequest (
    val name: String,
    val description: String = ""
    )
{}