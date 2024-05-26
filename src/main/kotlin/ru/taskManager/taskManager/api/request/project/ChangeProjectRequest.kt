package ru.taskManager.taskManager.api.request.project

data class ChangeProjectRequest
    (
    val name: String? = null,
    val description: String? = null
) {
    fun isEmpty(): Boolean {
        return name == null && description == null
    }
}