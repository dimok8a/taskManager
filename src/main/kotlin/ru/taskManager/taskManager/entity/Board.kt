package ru.taskManager.taskManager.entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull


@Entity
class Board(
    @Id
    @GeneratedValue
    override var id: Long? = null,
    @NotNull
    var name: String? = null,
    var description: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    val project: Project? = null,
    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var sections: MutableList<Section>? = null
) : GenericEntity() {
}