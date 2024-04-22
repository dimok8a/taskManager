package ru.taskManager.taskManager.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.jetbrains.annotations.NotNull
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
    @ManyToMany(fetch = FetchType.EAGER)
    var participants: MutableSet<User>? = null,
    @ManyToOne(fetch = FetchType.EAGER)
    var owner: User,
    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var boards: MutableList<Board>? = null
) : GenericEntity()