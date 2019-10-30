package com.alpex.domain.repo

import arrow.Kind
import com.alpex.domain.model.*

interface UserRepo<F> {
    fun find(id: UserId): Kind<F, User?>
    fun byName(name: String): Kind<F, List<User>>

    // NOTE: At least one
    fun add(user: User, vararg others: User): Kind<F, Unit>
}

