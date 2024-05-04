package ru.taskManager.taskManager.api.request.project

data class ChangeTaskDataRequest(
    val name: String? = null,
    val description: String? = null,
    val executorId: Long? = null,
    val inspectorId: Long? = null,
) {
    fun isEmpty(): Boolean {
        return name == null && description == null && inspectorId == null && executorId == null
    }
}
