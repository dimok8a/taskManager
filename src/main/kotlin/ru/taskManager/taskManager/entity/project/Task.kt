package ru.taskManager.taskManager.entity.project

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import ru.taskManager.taskManager.entity.GenericEntity
import ru.taskManager.taskManager.entity.user.User
import java.util.*

@Entity
class Task(
    @Id
    @GeneratedValue
    override var id: Long? = null,

    @Column(nullable = false)
    var name: String = "",

    var description: String? = null,

    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: Date,

    @ManyToOne(fetch = FetchType.LAZY)
    var executor: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var inspector: User? = null,

    @ManyToOne
    @JoinColumn(nullable = false)
    var section: Section

) : GenericEntity() {
}