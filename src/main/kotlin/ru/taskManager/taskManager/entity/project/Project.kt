package ru.taskManager.taskManager.entity.project

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.jetbrains.annotations.NotNull
import ru.taskManager.taskManager.entity.GenericEntity
import ru.taskManager.taskManager.entity.user.User
import java.util.Date

@Entity
class Project(
    @Id
    @GeneratedValue
    override var id: Long? = null,
    @NotNull
    var name: String? = null,
    var description: String? = null,
    @NotNull
    @CreationTimestamp
    @Column(nullable = false)
    val createdAt: Date,
    @ManyToMany(fetch = FetchType.LAZY)
    var participants: MutableSet<User>? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var owner: User,
    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var boards: MutableList<Board>? = null
) : GenericEntity()