package ru.taskManager.taskManager.entity

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name="users")
class User (
    @Id
    @GeneratedValue
    override var id: Long?,
    @NotNull
    @Column(nullable = false, unique = true)
    var nickname : String,
    @NotNull
    @Column(nullable = false)
    var firstname : String? = null,

    @Enumerated(EnumType.STRING)
    var role: Role? = null,

    @NotNull
    @Column(nullable = false)
    var lastname : String? = null,
    @NotNull
    @Column(nullable = false)
    var hash : String? = null,
//    var token : String? = null
    ) : GenericEntity(), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf();
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