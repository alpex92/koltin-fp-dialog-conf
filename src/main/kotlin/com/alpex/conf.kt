package com.alpex

import java.util.*

typealias Id = UUID

// Coproduct (sum)
sealed class Gender
object Male: Gender()
object Female: Gender()

// Product
data class UserInfo(val name: String, val lastName: String, val age: Int, val gender: Gender)

data class User(val id: Id, val info: UserInfo)

interface UserRepo {
    fun find(id: Id): User?
    fun byName(name: String): List<User>

    // NOTE: At least one
    fun add(user: User, vararg others: User): Unit
}

data class UserInput(val name: String, val age: Int, val gender: Gender)

