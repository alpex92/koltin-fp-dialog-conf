package com.alpex

import arrow.core.*
import arrow.core.extensions.nonemptylist.semigroup.semigroup
import arrow.core.extensions.validated.applicative.applicative

fun <A>A.condValidNel(check: (A) -> Boolean, error: String): ValidatedNel<String, A> =
    if (check(this))
        this.validNel()
    else
        error.invalidNel()

fun nonEmptyString(value: String, fieldName: String): ValidatedNel<String, String> =
    value.condValidNel(String::isNotEmpty, "$fieldName should be filled")

class UserInfoValidator {

    companion object {
        val app = Validated.applicative<NonEmptyList<String>>(Nel.semigroup())
    }

    fun validate(input: UserInfo): ValidatedNel<String, UserInfo> {
        return app.map(
            nonEmptyString(input.name, "Name"),
            nonEmptyString(input.lastName, "Last name"),
            input.age.condValidNel({age -> age >= 18}, "Age should be > 18")
        ) { (name, lastName, age) -> UserInfo(name, lastName, age, input.gender) }
        .fix()
    }
}