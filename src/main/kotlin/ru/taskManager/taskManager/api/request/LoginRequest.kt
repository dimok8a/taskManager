package ru.taskManager.taskManager.api.request


data class LoginRequest (
    val nickname: String,
    val hash: String,
    val rnd: Int
) {}