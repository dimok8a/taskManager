package ru.taskManager.taskManager.entity.project

enum class ESectionType(
    val typeString: String
) {
    PREPARED("Готовы к исполнению"),
    IN_PROGRESS("В процессе"),
    ON_CHECK("На проверке"),
    DONE("Готово")
}