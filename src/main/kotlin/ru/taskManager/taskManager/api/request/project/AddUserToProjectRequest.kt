package ru.taskManager.taskManager.api.request.project

data class AddUserToProjectRequest (
    val newUserId: Long
) {
    fun isEmpty(): Boolean {
        return newUserId == 0L
    }
}