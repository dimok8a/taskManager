package ru.taskManager.taskManager.entity.user

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.taskManager.taskManager.entity.GenericEntity
import java.util.Collections

@Entity
@Table(name="users")
class User (
    @NotNull
    @Column(nullable = false, unique = true)
    var nickname : String,
    @NotNull
    @Column(nullable = false)
    var firstname : String? = null,


    @NotNull
    @Column(nullable = false)
    var lastname : String? = null,
    @NotNull
    @Column(nullable = false)
    var hash : String? = null,

    var token : String? = null,

    @Enumerated(EnumType.STRING)
    var role: Role? = Role.USER,

    ) : GenericEntity(), UserDetails {

    @Id
    @GeneratedValue
    override var id: Long? = null

    override fun getAuthorities(): List<GrantedAuthority> {
        return role?.getAuthorities() ?: Collections.emptyList();
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