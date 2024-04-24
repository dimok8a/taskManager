package ru.taskManager.taskManager.entity.project

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import ru.taskManager.taskManager.entity.GenericEntity


@Entity
class Board(
    @Id
    @GeneratedValue
    override var id: Long? = null,
    @NotNull
    var name: String? = null,
    var description: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    val project: Project? = null,
    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var sections: MutableList<Section>? = null
) : GenericEntity() {
}