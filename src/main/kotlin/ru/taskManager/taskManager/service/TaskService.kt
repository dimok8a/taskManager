package ru.taskManager.taskManager.service

import ru.taskManager.taskManager.api.request.project.ChangeTaskDataRequest
import ru.taskManager.taskManager.api.request.project.NewTaskRequest
import ru.taskManager.taskManager.entity.project.Task
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.TaskRepository


interface TaskService {
    val repository: TaskRepository
    fun createNewTask(request: NewTaskRequest, creator: User): Task

    fun getTaskByUserAndTaskId(user: User, id: Long): Task?
    fun getAllExecutorTasks(executor: User): List<Task>
    fun getAllInspectorTasks(inspector: User): List<Task>


    fun changeTaskData(request: ChangeTaskDataRequest, user: User, task: Task): Task
    fun takeTaskOnExecution(executor: User, task: Task): Task
    fun completeTask(executor: User, task: Task): Task
    fun checkTask(inspector: User, task: Task): Task
    fun rejectTask(inspector: User, task: Task): Task

    fun deleteTask(user: User, taskId: Long)
}