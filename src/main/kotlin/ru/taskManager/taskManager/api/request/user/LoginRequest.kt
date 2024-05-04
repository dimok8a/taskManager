package ru.taskManager.taskManager.api.request.user


data class LoginRequest(
    val nickname: String,
    val hash: String,
    val rnd: Int
)