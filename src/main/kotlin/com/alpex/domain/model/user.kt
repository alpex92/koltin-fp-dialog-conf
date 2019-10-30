package com.alpex.domain.model

import com.alpex.domain.validation.Adult
import com.alpex.domain.validation.NonEmptyString
import java.util.*

sealed class Gender
object Male: Gender()
object Female: Gender()

inline class Age(val value: Int)

inline class UserId(val id: UUID) {
    companion object {
        fun new() = UserId(UUID.randomUUID())
    }
}

data class UserInfo(
    val name: NonEmptyString,
    val lastName: NonEmptyString,
    val age: Adult, // TODO: BirthDate?
    val gender: Gender
)

data class User(val id: UserId, val info: UserInfo)