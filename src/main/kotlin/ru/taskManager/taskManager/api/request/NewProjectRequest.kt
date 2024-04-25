package ru.taskManager.taskManager.api.request

data class NewProjectRequest (
    val name: String,
    val description: String? = ""
    )
{}