package ru.taskManager.taskManager.api.response

import com.fasterxml.jackson.annotation.JsonProperty


data class AuthenticationResponse
    (
    @JsonProperty("accessToken")
    val accessToken: String? = null,
    @JsonProperty("id")
    val id: Long? = null
)