package ru.taskManager.taskManager.entity.user

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.taskManager.taskManager.entity.GenericEntity
import ru.taskManager.taskManager.entity.project.Task
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, unique = true)
    var nickname: String,
    @Column(nullable = false)
    var firstname: String? = null,


    @Column(nullable = false)
    var lastname: String? = null,
    @Column(nullable = false)
    var hash: String? = null,

    var token: String? = null,

    @Enumerated(EnumType.STRING)
    var role: Role? = Role.USER,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "executor")
    var tasks: MutableList<Task> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inspector")
    var tasksToCheck: MutableList<Task> = mutableListOf(),

    ) : GenericEntity(), UserDetails {

    @Id
    @GeneratedValue
    override var id: Long? = null

    override fun getAuthorities(): List<GrantedAuthority> {
        return role?.getAuthorities() ?: Collections.emptyList()
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return nickname
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}