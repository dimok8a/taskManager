package ru.taskManager.taskManager.api.request

data class RegisterRequest(
    val nickname : String,
    val firstname : String,
    val lastname : String,
    val hash : String
)
