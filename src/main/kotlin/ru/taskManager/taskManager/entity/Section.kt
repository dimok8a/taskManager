package ru.taskManager.taskManager.entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull


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