package ru.taskManager.taskManager.entity.project

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import ru.taskManager.taskManager.entity.GenericEntity
import ru.taskManager.taskManager.entity.user.User
import java.util.*

@Entity
class Project(
    @Id
    @GeneratedValue
    override var id: Long? = null,

    var name: String? = null,
    var description: String? = null,
    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: Date,
    @ManyToMany(fetch = FetchType.LAZY)
    var participants: MutableSet<User> = mutableSetOf(),
    @ManyToOne(fetch = FetchType.LAZY)
    var owner: User,
    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var boards: MutableList<Board> = mutableListOf()
) : GenericEntity()