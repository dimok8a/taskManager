package ru.taskManager.taskManager.entity

enum class ESectionType(
    val typeString: String
) {
    PREPARED("Prepared"),
    IN_PROGRESS("In progress"),
    ON_CHECK("On check"),
    DONE("Done")
}