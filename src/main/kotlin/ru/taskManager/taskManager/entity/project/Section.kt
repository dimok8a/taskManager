package ru.taskManager.taskManager.entity.project

import jakarta.persistence.*
import ru.taskManager.taskManager.entity.GenericEntity


@Entity
class Section(
    @Id
    @GeneratedValue
    override var id: Long? = null,

    val type: ESectionType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    val board: Board,

    @OneToMany(mappedBy = "section", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val tasks: MutableList<Task> = mutableListOf(),

    ) : GenericEntity() {
        fun dismissTask(task: Task) {
            tasks.remove(task)
        }
    }