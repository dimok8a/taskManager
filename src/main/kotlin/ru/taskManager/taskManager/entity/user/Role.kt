package ru.taskManager.taskManager.entity.user

import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.Collections
import java.util.stream.Collectors

enum class Role(
    private val permissions: Set<Permission>
) {
    USER(Collections.emptySet<Permission>());

    fun getAuthorities(): List<SimpleGrantedAuthority> {
        val authorities = permissions
            .stream()
            .map { permission -> SimpleGrantedAuthority(permission.name) }
            .collect(Collectors.toList())
        authorities.add(SimpleGrantedAuthority("ROLE_$name"))
        return authorities
    }
}