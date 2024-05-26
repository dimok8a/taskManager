package ru.taskManager.taskManager.api.request.project

data class AddUserToProjectRequest (
    val newUserNickname: String
) {
    fun isEmpty(): Boolean {
        return newUserNickname == ""
    }
}