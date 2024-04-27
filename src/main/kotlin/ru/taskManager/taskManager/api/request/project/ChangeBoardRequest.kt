package ru.taskManager.taskManager.api.request.project

import com.fasterxml.jackson.annotation.JsonProperty

data class ChangeBoardRequest
    (
    val name: String? = null,
    val description: String? = null,
    @JsonProperty(value = "inspector_id")
    val inspectorId: Long?
){
    fun isEmpty() : Boolean {
        return name == null && description == null && inspectorId == null
    }
}