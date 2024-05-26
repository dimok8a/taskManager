package ru.taskManager.taskManager.api.response

import ru.taskManager.taskManager.entity.user.User

data class UserDataResponse (
    val id: Long,
    val nickname: String,
    val firstname: String,
    val lastname: String,
) {
    constructor(user: User) : this(
        id = user.id?:0,
        nickname = user.nickname,
        firstname = user.firstname?:"",
        lastname = user.lastname?:""
    )
}