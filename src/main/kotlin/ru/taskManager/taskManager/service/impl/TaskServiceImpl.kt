package ru.taskManager.taskManager.service.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.taskManager.taskManager.api.request.project.ChangeTaskDataRequest
import ru.taskManager.taskManager.api.request.project.NewTaskRequest
import ru.taskManager.taskManager.entity.project.ESectionType
import ru.taskManager.taskManager.entity.project.Task
import ru.taskManager.taskManager.entity.user.User
import ru.taskManager.taskManager.repository.SectionRepository
import ru.taskManager.taskManager.repository.TaskRepository
import ru.taskManager.taskManager.service.*
import java.util.*

@Service
class TaskServiceImpl(
    override val repository: TaskRepository,
    private val boardService: BoardService,
    private val userService: UserService,
    private val projectValidationService: ProjectValidationService,
    private val boardValidationService: BoardValidationService,
    private val sectionRepository: SectionRepository,
) : TaskService {


    override fun createNewTask(request: NewTaskRequest, creator: User): Task {
        val board = boardService.getBoardByUserAndBoardId(creator, request.boardId)
            ?: throw Exception("У пользователя не нашлось указанной доски")
        val section = boardService.getFirstSection(board) ?: throw Exception("Ошибка поиска секции")
        val newTask =
            Task(name = request.name, description = request.description, createdAt = Date(), section = section)

        var inspector = request.inspectorId?.let { userService.getUserById(it) }
        if (inspector != null && !projectValidationService.checkIfUserHasProject(inspector, board.project)) inspector =
            null
        var executor = request.executorId?.let { userService.getUserById(it) }
        if (executor != null && !projectValidationService.checkIfUserHasProject(executor, board.project)) executor =
            null
        newTask.inspector = inspector
        newTask.executor = executor

        return repository.save(newTask)
    }

    override fun getTaskByUserAndTaskId(user: User, id: Long): Task? {
        val task = repository.findByIdOrNull(id)
        if (task != null && boardValidationService.checkIfUserHasBoard(user, task.section.board)) return task
        return null
    }

    override fun getAllExecutorTasks(executor: User): List<Task> {
        val foundUser = userService.getUserById(executor.id ?: throw Exception("Ошибка при получении id пользователя!"))
        return foundUser?.tasks ?: throw Exception("Не удалось найти пользователя")
    }

    override fun getAllInspectorTasks(inspector: User): List<Task> {
        val foundUser = userService.getUserById(inspector.id ?: throw Exception("Ошибка при получении id пользователя!"))
        return foundUser?.tasksToCheck ?: throw Exception("Не удалось найти пользователя")
    }

    override fun changeTaskData(request: ChangeTaskDataRequest, user: User, task: Task): Task {
        if (!boardValidationService.checkIfUserHasBoard(
                user,
                task.section.board
            )
        ) throw Exception("Не удалось найти задачу у пользователя")
        if (request.name != null) task.name = request.name
        if (request.description != null) task.description = request.description
        val inspector = request.inspectorId?.let { userService.getUserById(it) }
        if (inspector != null && boardValidationService.checkIfUserHasBoard(inspector, task.section.board))
            task.inspector = inspector
        val executor = request.executorId?.let { userService.getUserById(it) }
        if (executor != null && boardValidationService.checkIfUserHasBoard(executor, task.section.board))
            task.executor = executor
        return repository.save(task)
    }

    override fun takeTaskOnExecution(executor: User, task: Task): Task {
        if (!boardValidationService.checkIfUserHasBoard(
                executor,
                task.section.board
            )
        ) throw Exception("Не удалось найти задачу у пользователя")
        if (task.executor != null && task.executor != executor) throw Exception("Исполнитель уже указан и это не вы")
        if (task.executor == null) task.executor = executor
        if (task.section.type != ESectionType.PREPARED) throw Exception("Ошибка в секции задачи. Взять задачу не из секции 'Готовы к исполнению' невозможно")
        val newSection = boardService.getNextSection(task.section, task.section.board)
            ?: throw Exception("Не удалось найти следующую секцию")
        task.section = newSection
        return repository.save(task)
    }

    override fun completeTask(executor: User, task: Task): Task {
        if (!boardValidationService.checkIfUserHasBoard(
                executor,
                task.section.board
            )
        ) throw Exception("Не удалось найти задачу у пользователя")
        if (task.executor != null && task.executor != executor) throw Exception("Указанный в задаче исполнитель не соответствует переданному")
        if (task.executor == null) throw Exception("Не указан исполнитель. Дальнейшая операция невозможна")
        if (task.section.type != ESectionType.IN_PROGRESS) throw Exception("Ошибка в секции задачи. Выполнение задач не из секции 'В процессе' невозможно")
//      Move to "On check" section
        var newSection = boardService.getNextSection(task.section, task.section.board)
            ?: throw Exception("Не удалось найти следующую секцию")
//      Move to "Done" section
        if (task.inspector == null)
            newSection = boardService.getNextSection(newSection, task.section.board)
                ?: throw Exception("Не удалось найти следующую секцию")
        task.section = newSection
        return repository.save(task)
    }

    private fun validateInspector(inspector: User, task: Task) {
        if (!boardValidationService.checkIfUserHasBoard(
                inspector,
                task.section.board
            )
        ) throw Exception("Не удалось найти задачу у пользователя")
        if (task.inspector != null && task.inspector != inspector) throw Exception("Указанный в задаче проверяющий не соответствует переданному")
        if (task.inspector == null) throw Exception("Не указан проверяющий. Дальнейшая операция невозможна")
    }

    override fun checkTask(inspector: User, task: Task): Task {
        validateInspector(inspector, task)
        if (task.section.type != ESectionType.ON_CHECK) throw Exception("Ошибка в секции задачи. Проверка задач не из секции 'На проверке' невозможна")
        val newSection = boardService.getNextSection(task.section, task.section.board)
            ?: throw Exception("Не удалось найти следующую секцию")
        task.section = newSection
        return repository.save(task)
    }

    override fun rejectTask(inspector: User, task: Task): Task {
        validateInspector(inspector, task)
        if (task.section.type != ESectionType.ON_CHECK) throw Exception("Ошибка в секции задачи. Проверка задач не из секции 'На проверке' невозможна")
        val newSection = boardService.getPrevSection(task.section, task.section.board)
            ?: throw Exception("Не удалось найти следующую секцию")
        task.section = newSection
        return repository.save(task)
    }

    override fun deleteTask(user: User, taskId: Long) {
        val task = getTaskByUserAndTaskId(user, taskId) ?: throw Exception("Не удалось найти задачу у пользователя")
        val taskSection = task.section
        taskSection.dismissTask(task)
        repository.delete(task)
    }
}