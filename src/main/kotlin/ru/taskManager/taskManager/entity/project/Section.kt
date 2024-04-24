package ru.taskManager.taskManager.entity.project

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import ru.taskManager.taskManager.entity.GenericEntity


@Entity
class Section(
    @Id
    @GeneratedValue
    override var id: Long? = null,

    @NotNull
    val type: ESectionType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    val board: Board
) : GenericEntity()