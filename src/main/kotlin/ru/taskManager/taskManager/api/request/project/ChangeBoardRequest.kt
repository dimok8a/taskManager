package ru.taskManager.taskManager.api.request.project

data class ChangeBoardRequest
    (
    val name: String? = null,
    val description: String? = null,
    val inspectorId: Long?
) {
    fun isEmpty(): Boolean {
        return name == null && description == null && inspectorId == null
    }
}