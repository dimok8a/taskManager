package ru.taskManager.taskManager.entity.project

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.jetbrains.annotations.NotNull
import org.yaml.snakeyaml.inspector.TagInspector
import ru.taskManager.taskManager.entity.GenericEntity
import ru.taskManager.taskManager.entity.user.User
import java.util.*

@Entity
class Task(
    @Id
    @GeneratedValue
    override var id: Long? = null,

    @NotNull
    @Column(nullable = false)
    var name: String = "",

    var description: String? = null,

    @NotNull
    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: Date,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var executor: User? = null,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var inspector: User? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(nullable = false)
    var section: Section

    ): GenericEntity() {}