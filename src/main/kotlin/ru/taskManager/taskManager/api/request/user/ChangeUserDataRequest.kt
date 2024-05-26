package ru.taskManager.taskManager.api.request.user

data class ChangeUserDataRequest (
    val firstname: String?,
    val lastname: String?
) {
    fun isEmpty(): Boolean {
        return firstname == null && lastname == null
    }
}