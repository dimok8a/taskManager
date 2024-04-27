package ru.taskManager.taskManager.entity.project

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import ru.taskManager.taskManager.entity.GenericEntity
import ru.taskManager.taskManager.entity.user.User


@Entity
class Board(
    @Id
    @GeneratedValue
    override var id: Long? = null,
    @NotNull
    var name: String? = "Доска",
    var description: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    val project: Project,
    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var sections: MutableList<Section> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var inspectorByDefault: User? = null,
) : GenericEntity() {
}